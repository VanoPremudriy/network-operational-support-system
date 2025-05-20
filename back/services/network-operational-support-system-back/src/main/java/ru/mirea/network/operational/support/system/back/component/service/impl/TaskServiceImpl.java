package ru.mirea.network.operational.support.system.back.component.service.impl;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.network.operational.support.system.back.component.repository.TaskRepository;
import ru.mirea.network.operational.support.system.back.component.service.CalculateRouteService;
import ru.mirea.network.operational.support.system.back.component.service.TaskService;
import ru.mirea.network.operational.support.system.back.dictionary.Constant;
import ru.mirea.network.operational.support.system.back.exception.TaskException;
import ru.mirea.network.operational.support.system.back.zookeeper.DistributedLock;
import ru.mirea.network.operational.support.system.db.dictionary.TaskType;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;
import ru.mirea.network.operational.support.system.db.entity.TaskStatus;
import ru.mirea.network.operational.support.system.python.api.calculate.CalculateRouteRq;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRq;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RefreshScope
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final CuratorFramework curatorFramework;
    private final TaskRepository taskRepository;
    private final CalculateRouteService calculateRouteService;
    private final JsonMapper jsonMapper;

    @Value("${zookeeper.waitTime:PT10M}")
    private final Duration waitTime;

    @Override
    public TaskEntity createTaskWithLock(UUID clientId, TaskType taskType, Object data) {
        try (DistributedLock ignored = new DistributedLock(curatorFramework, Constant.TASK_LOCK_CODE, waitTime)) {
            TaskEntity taskEntity = taskRepository.findByActiveFlagTrueAndStatus(TaskStatus.IN_PROGRESS);
            if (taskEntity != null) {
                throw new TaskException("Найдена активная задача. Попробуйте позже");
            }

            return taskRepository.save(new TaskEntity()
                    .setCreatedTime(LocalDateTime.now())
                    .setActiveFlag(true)
                    .setExecutionCount(1)
                    .setClientId(clientId)
                    .setTaskType(taskType.name())
                    .setTaskData(jsonMapper.valueToTree(data))
                    .setStatus(TaskStatus.IN_PROGRESS));
        } catch (Exception e) {
            log.error("Ошибка при попытке открыть задачу", e);
            throw new TaskException("При попытке открыть задачу произошла ошибка. Попробуйте позже");
        }
    }

    @Override
    public void processTask(TaskEntity taskEntity) {
        switch (taskEntity.getTaskType()) {
            case CALCULATE_ROUTE -> calculateRoute(taskEntity);
        }
        ;
    }

    private void calculateRoute(TaskEntity taskEntity) {
        try {
            CreateRouteRq rq = jsonMapper.treeToValue(taskEntity.getTaskData(), CreateRouteRq.class);
            CalculateRouteRq calculateRouteRq = CalculateRouteRq.builder()
                    .city1(rq.getStartingPoint())
                    .city2(rq.getDestinationPoint())
                    .build();
            taskEntity.setRoutes(calculateRouteService.calculate(taskEntity, calculateRouteRq, rq.getCapacity()));

            saveTask(taskEntity.setResolvedDate(LocalDateTime.now()).setStatus(TaskStatus.SELECTION_IS_REQUIRED));
        } catch (TaskException e) {
            log.error("Ошибка при попытке рассчитать маршрут: {}. Задача не будет выполнена.", e.getMessage(), e);

            saveTask(taskEntity.setActiveFlag(false).setResolvedDate(LocalDateTime.now()).setStatus(TaskStatus.FAILED));
        } catch (Exception e) {
            log.error("Ошибка при попытке рассчитать маршрут", e);
        }
    }

    @Transactional
    private void saveTask(TaskEntity taskEntity) {
        taskRepository.save(taskEntity);
    }

}

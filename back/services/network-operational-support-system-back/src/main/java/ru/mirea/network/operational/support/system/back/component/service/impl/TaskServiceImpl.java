package ru.mirea.network.operational.support.system.back.component.service.impl;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.mirea.network.operational.support.system.back.component.mapper.RootMapper;
import ru.mirea.network.operational.support.system.back.component.repository.TaskRepository;
import ru.mirea.network.operational.support.system.back.component.service.CalculateRouteService;
import ru.mirea.network.operational.support.system.back.component.service.TaskService;
import ru.mirea.network.operational.support.system.back.dictionary.Constant;
import ru.mirea.network.operational.support.system.back.exception.NotFoundException;
import ru.mirea.network.operational.support.system.back.exception.TaskException;
import ru.mirea.network.operational.support.system.back.zookeeper.DistributedLock;
import ru.mirea.network.operational.support.system.db.dictionary.TaskType;
import ru.mirea.network.operational.support.system.db.entity.RouteEntity;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;
import ru.mirea.network.operational.support.system.python.api.calculate.CalculateRouteRq;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRq;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RefreshScope
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final CuratorFramework curatorFramework;
    private final TaskRepository taskRepository;
    private final RootMapper rootMapper;
    private final CalculateRouteService calculateRouteService;
    private final JsonMapper jsonMapper;

    @Value("${zookeeper.waitTime:PT10M}")
    private final Duration waitTime;

    @Override
    public TaskEntity createTaskWithLock(UUID clientId, TaskType taskType, Object data) {
        try (DistributedLock ignored = new DistributedLock(curatorFramework, Constant.TASK_LOCK_CODE, waitTime)) {
            TaskEntity taskEntity = taskRepository.findByActiveFlagTrue();
            if (taskEntity != null) {
                throw new TaskException("Найдена активная задача. Попробуйте позже");
            }

            return taskRepository.save(new TaskEntity()
                    .setCreatedTime(LocalDateTime.now())
                    .setActiveFlag(true)
                    .setExecutionCount(1)
                    .setClientId(clientId)
                    .setTaskType(taskType.name())
                    .setTaskData(jsonMapper.valueToTree(data)));
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

    @Transactional
    @Override
    public void applyTask(UUID taskId) {
        TaskEntity taskEntity = taskRepository.findByIdWithRoute(taskId);

        if (taskEntity == null) {
            throw new NotFoundException("Не найдена задача с ID: " + taskId);
        }

        if (CollectionUtils.isEmpty(taskEntity.getRoutes())) {
            throw new NotFoundException("Не найден маршрут для задачи с ID: " + taskId);
        }

        // Пока предполагается, что будет только один маршрут.
        RouteEntity routeEntity = taskEntity.getRoutes().stream().findAny().get();

        routeEntity.setActiveFlag(true);

        //TODO Сохранить маршрут в БД.
        routeEntity.getRouteData();

        taskRepository.save(taskEntity.setActiveFlag(false));
    }

    private void calculateRoute(TaskEntity taskEntity) {
        try {
            CreateRouteRq rq = jsonMapper.treeToValue(taskEntity.getTaskData(), CreateRouteRq.class);
            CalculateRouteRq calculateRouteRq = CalculateRouteRq.builder()
                    .city1(rq.getStartingPoint())
                    .city2(rq.getDestinationPoint())
                    .build();
            taskEntity.setRoutes(Set.of(calculateRouteService.calculate(taskEntity, calculateRouteRq, rq.getCapacity())));

            taskRepository.save(taskEntity.setResolvedDate(LocalDateTime.now()));
        } catch (TaskException e) {
            log.error("Ошибка при попытке рассчитать маршрут: {}. Задача не будет выполнена.", e.getMessage(), e);

            taskRepository.save(taskEntity.setActiveFlag(false).setResolvedDate(LocalDateTime.now()));
        } catch (Exception e) {
            log.error("Ошибка при попытке рассчитать маршрут", e);
        }
    }

}

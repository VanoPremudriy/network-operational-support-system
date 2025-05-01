package ru.mirea.network.operational.support.system.back.component.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.back.component.mapper.RootMapper;
import ru.mirea.network.operational.support.system.back.component.repository.TaskRepository;
import ru.mirea.network.operational.support.system.back.component.service.CalculateRouteService;
import ru.mirea.network.operational.support.system.back.component.service.TaskService;
import ru.mirea.network.operational.support.system.back.dictionary.Constant;
import ru.mirea.network.operational.support.system.back.exception.TaskException;
import ru.mirea.network.operational.support.system.back.zookeeper.DistributedLock;
import ru.mirea.network.operational.support.system.db.dictionary.TaskType;
import ru.mirea.network.operational.support.system.db.entity.RouteEntity;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRq;

import java.time.Duration;
import java.time.OffsetDateTime;
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
                    .setCreatedTime(OffsetDateTime.now())
                    .setActiveFlag(true)
                    .setExecutionCount(1)
                    .setClientId(clientId)
                    .setTaskType(taskType.name())
                    .setTaskData(data));
        } catch (Exception e) {
            log.error("Ошибка при попытке открыть задачу", e);
            throw new TaskException("При попытке открыть задачу произошла ошибка. Попробуйте позже");
        }
    }

    @Override
    public void processTask(TaskEntity taskEntity) {
        boolean completed = switch (taskEntity.getTaskType()) {
            case CALCULATE_ROUTE -> calculateRoute(taskEntity);
        };

        if (completed) {
            taskRepository.save(taskEntity
                    .setResolvedDate(OffsetDateTime.now())
                    .setActiveFlag(false));
        }
    }

    private boolean calculateRoute(TaskEntity taskEntity) {
        CreateRouteRq rq = (CreateRouteRq) taskEntity.getTaskData();

        RouteEntity route = calculateRouteService.calculate(rootMapper.map(rq, taskEntity));

        //TODO save

        return true;
    }

}

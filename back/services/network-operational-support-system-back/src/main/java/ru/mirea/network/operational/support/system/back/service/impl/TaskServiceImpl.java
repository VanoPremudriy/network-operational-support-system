package ru.mirea.network.operational.support.system.back.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.back.entity.TaskEntity;
import ru.mirea.network.operational.support.system.back.exception.TaskException;
import ru.mirea.network.operational.support.system.back.repository.TaskRepository;
import ru.mirea.network.operational.support.system.back.service.TaskService;
import ru.mirea.network.operational.support.system.back.util.Constant;
import ru.mirea.network.operational.support.system.back.zookeeper.DistributedLock;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final CuratorFramework curatorFramework;
    private final TaskRepository taskRepository;

    @Value("${zookeeper.waitTime:PT10M}")
    private Duration waitTime;

    @Override
    public TaskEntity createTaskWithLock(UUID clientId) {
        try (DistributedLock lock = new DistributedLock(curatorFramework, Constant.TASK_LOCK_CODE, waitTime)) {
            TaskEntity taskEntity = taskRepository.findByActiveFlagTrue();
            if (taskEntity != null) {
                throw new TaskException("Найдена активная задача. Попробуйте позже");
            }

            return taskRepository.save(new TaskEntity()
                    .setCreatedTime(LocalDateTime.now())
                    .setActiveFlag(true)
                    .setClientId(clientId));
        } catch (Exception e) {
            log.error("Ошибка при попытке открыть задачу", e);
            throw new TaskException("При попытке открыть задачу произошла ошибка. Попробуйте позже");
        }
    }
}

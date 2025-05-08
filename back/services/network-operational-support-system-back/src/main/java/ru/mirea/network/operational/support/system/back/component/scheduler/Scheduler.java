package ru.mirea.network.operational.support.system.back.component.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.mirea.network.operational.support.system.back.component.repository.TaskRepository;
import ru.mirea.network.operational.support.system.back.component.service.TaskService;
import ru.mirea.network.operational.support.system.back.dictionary.Constant;
import ru.mirea.network.operational.support.system.back.zookeeper.DistributedLock;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Component
@RefreshScope
@RequiredArgsConstructor
public class Scheduler {

    private final CuratorFramework curatorFramework;
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @Value("${zookeeper.waitTime:PT10M}")
    private final Duration waitTime;

    @Value("${scheduler.waitForConfirmedTime:PT05H}")
    private final Duration waitForConfirmedTime;

    @Value("${scheduler.maxExecutions:4}")
    private final Integer maxExecutions;

    @Scheduled(fixedDelayString = "${scheduler.interval:PT15M}")
    public void process() {
        try (DistributedLock ignored = new DistributedLock(curatorFramework, Constant.SCHEDULE_LOCK_CODE, waitTime)) {
            TaskEntity taskEntity = taskRepository.findByActiveFlagTrue();

            if (taskEntity == null) {
                return;
            }

            if (taskEntity.getExecutionCount() >= maxExecutions) {
                taskRepository.save(taskEntity
                        .setResolvedDate(LocalDateTime.now())
                        .setActiveFlag(false));
                return;
            }

            if (taskEntity.getResolvedDate() != null) {
                if (waitForConfirmedTime.compareTo(Duration.between(taskEntity.getResolvedDate(), LocalDateTime.now())) < 0) {
                    taskRepository.save(taskEntity.setActiveFlag(false));
                }
                return;
            }

            taskEntity = taskRepository.save(taskEntity.setExecutionCount(taskEntity.getExecutionCount() + 1));

            taskService.processTask(taskEntity);
        } catch (Exception e) {
            log.error("Ошибка при попытке докатить задачу", e);
        }
    }
}

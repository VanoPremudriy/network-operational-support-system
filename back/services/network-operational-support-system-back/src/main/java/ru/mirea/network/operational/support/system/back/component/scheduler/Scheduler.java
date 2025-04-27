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

    @Scheduled(fixedDelayString = "${scheduler.interval}")
    public void process() {
        try (DistributedLock ignored = new DistributedLock(curatorFramework, Constant.SCHEDULE_LOCK_CODE, waitTime)) {
            //TODO: find task
            TaskEntity taskEntity = new TaskEntity();

            //TODO: update task

            taskService.processTask(taskEntity);
        } catch (Exception e) {
            log.error("Ошибка при попытке докатить задачу", e);
        }
    }
}

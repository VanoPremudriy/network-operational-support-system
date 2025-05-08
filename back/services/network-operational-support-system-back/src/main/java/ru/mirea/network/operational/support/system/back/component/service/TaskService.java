package ru.mirea.network.operational.support.system.back.component.service;

import ru.mirea.network.operational.support.system.db.dictionary.TaskType;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;

import java.util.UUID;

public interface TaskService {
    TaskEntity createTaskWithLock(UUID clientId, TaskType taskType, Object data);

    void processTask(TaskEntity taskEntity);

    void applyTask(UUID taskId);
}

package ru.mirea.network.operational.support.system.back.service;

import ru.mirea.network.operational.support.system.back.dictionary.ParamType;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;

import java.util.Map;
import java.util.UUID;

public interface TaskService {
    TaskEntity createTaskWithLock(UUID clientId);

    void processTask(TaskEntity taskEntity);

    void processTask(TaskEntity taskEntity, Map<ParamType, Object> params);
}

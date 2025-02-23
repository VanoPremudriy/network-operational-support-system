package ru.mirea.network.operational.support.system.back.service;

import ru.mirea.network.operational.support.system.back.entity.TaskEntity;

import java.util.UUID;

public interface TaskService {
    TaskEntity createTaskWithLock(UUID clientId);
}

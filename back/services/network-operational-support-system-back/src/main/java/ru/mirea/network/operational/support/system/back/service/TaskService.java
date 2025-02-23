package ru.mirea.network.operational.support.system.back.service;

import ru.mirea.network.operational.support.system.back.entity.TaskEntity;

public interface TaskService {
    TaskEntity createTask(String clientId);
}

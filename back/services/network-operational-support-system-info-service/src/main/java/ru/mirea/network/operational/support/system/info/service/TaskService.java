package ru.mirea.network.operational.support.system.info.service;

import ru.mirea.network.operational.support.system.info.api.info.task.DetailedTaskRs;
import ru.mirea.network.operational.support.system.info.api.info.task.TaskListRs;

import java.util.UUID;

public interface TaskService {
    TaskListRs getByClientId(UUID clientId);

    DetailedTaskRs getById(UUID id);
}

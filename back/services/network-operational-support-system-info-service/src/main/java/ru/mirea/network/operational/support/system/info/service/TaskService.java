package ru.mirea.network.operational.support.system.info.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.mirea.network.operational.support.system.info.api.task.DetailedTaskRs;
import ru.mirea.network.operational.support.system.info.api.task.GetAllTasksRq;
import ru.mirea.network.operational.support.system.info.api.task.GetAllTasksRs;
import ru.mirea.network.operational.support.system.info.api.task.TaskListRs;

import java.util.UUID;

public interface TaskService {
    TaskListRs getByClientId(UUID clientId);

    DetailedTaskRs getById(UUID id);

    GetAllTasksRs getAllTasks(UUID employeeId, GetAllTasksRq rq); //throws JsonProcessingException;
}

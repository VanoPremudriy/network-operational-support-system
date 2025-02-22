package ru.mirea.network.operational.support.system.back.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.api.create.route.CreateRouteRq;
import ru.mirea.network.operational.support.system.back.entity.TaskEntity;
import ru.mirea.network.operational.support.system.back.repository.TaskRepository;
import ru.mirea.network.operational.support.system.back.service.TaskService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    TaskRepository taskRepository;

    @Override
    public TaskEntity createTask(String clientId) {

        TaskEntity taskEntity = new TaskEntity()
                .setCreatedTime(LocalDateTime.now())
                .setActiveFlag(true)
                .setClientId(clientId);

        return taskRepository.save(taskEntity);
    }
}

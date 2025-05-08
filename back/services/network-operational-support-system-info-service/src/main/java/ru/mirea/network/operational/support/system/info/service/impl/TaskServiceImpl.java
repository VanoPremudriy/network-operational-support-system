package ru.mirea.network.operational.support.system.info.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.common.api.ErrorDTO;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;
import ru.mirea.network.operational.support.system.info.api.task.DetailedTaskRs;
import ru.mirea.network.operational.support.system.info.api.task.TaskListRs;
import ru.mirea.network.operational.support.system.info.mapper.EntityMapper;
import ru.mirea.network.operational.support.system.info.repository.TaskRepository;
import ru.mirea.network.operational.support.system.info.service.TaskService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final EntityMapper entityMapper;

    @Override
    public TaskListRs getByClientId(UUID clientId) {
        List<TaskEntity> taskList = taskRepository.findAllByClientId(clientId);

        return TaskListRs.builder()
                .body(entityMapper.mapTask(taskList))
                .success(true)
                .build();
    }

    @Override
    public DetailedTaskRs getById(UUID id) {
        TaskEntity taskEntity = taskRepository.findByTaskIdWithRoutes(id);
        if (taskEntity == null) {
            return DetailedTaskRs.builder()
                    .error(ErrorDTO.builder()
                            .title("Задача [" + id + "] не найдена")
                            .build())
                    .success(false)
                    .build();
        }

        return DetailedTaskRs.builder()
                .body(entityMapper.mapDetailed(taskEntity))
                .success(true)
                .build();
    }
}

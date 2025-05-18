package ru.mirea.network.operational.support.system.info.service.impl;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.mirea.network.operational.support.system.common.api.ErrorDTO;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;
import ru.mirea.network.operational.support.system.info.api.task.DetailedTask;
import ru.mirea.network.operational.support.system.info.api.task.DetailedTaskRs;
import ru.mirea.network.operational.support.system.info.api.task.Route;
import ru.mirea.network.operational.support.system.info.api.task.TaskListRs;
import ru.mirea.network.operational.support.system.info.mapper.EntityMapper;
import ru.mirea.network.operational.support.system.info.repository.TaskRepository;
import ru.mirea.network.operational.support.system.info.service.TaskService;
import ru.mirea.network.operational.support.system.route.api.route.common.RouteInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final EntityMapper entityMapper;
    private final JsonMapper mapper;

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

        DetailedTask detailedTask = entityMapper.mapDetailed(taskEntity);
        detailedTask.setRoutes(new ArrayList<>());

        if (!CollectionUtils.isEmpty(taskEntity.getRoutes())) {
            taskEntity.getRoutes().forEach(route -> {
                try {
                    RouteInfo routeInfo = mapper.treeToValue(route.getRouteData(), RouteInfo.class);
                    detailedTask.getRoutes().add(Route.builder()
                            .distance(routeInfo.getDistance())
                            .shifts(routeInfo.getShifts())
                            .price(route.getPrice())
                            .build());
                } catch (Exception e) {
                    throw new RuntimeException("Не удалось десериализовать маршрут.");
                }
            });
        }

        return DetailedTaskRs.builder()
                .body(detailedTask)
                .success(true)
                .build();
    }
}

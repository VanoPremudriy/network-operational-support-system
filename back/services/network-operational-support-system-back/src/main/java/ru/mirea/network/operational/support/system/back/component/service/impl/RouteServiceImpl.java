package ru.mirea.network.operational.support.system.back.component.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.network.operational.support.system.back.common.Node;
import ru.mirea.network.operational.support.system.back.component.mapper.EntityMapper;
import ru.mirea.network.operational.support.system.back.component.repository.NodeRepository;
import ru.mirea.network.operational.support.system.back.component.repository.RouteRepository;
import ru.mirea.network.operational.support.system.back.component.repository.TaskRepository;
import ru.mirea.network.operational.support.system.back.component.service.RouteService;
import ru.mirea.network.operational.support.system.back.component.service.TaskService;
import ru.mirea.network.operational.support.system.back.dictionary.Constant;
import ru.mirea.network.operational.support.system.back.exception.NotFoundException;
import ru.mirea.network.operational.support.system.back.exception.TaskException;
import ru.mirea.network.operational.support.system.common.api.ErrorDTO;
import ru.mirea.network.operational.support.system.common.dictionary.ErrorCode;
import ru.mirea.network.operational.support.system.db.dictionary.TaskType;
import ru.mirea.network.operational.support.system.db.entity.NodeEntity;
import ru.mirea.network.operational.support.system.db.entity.RouteEntity;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRq;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRs;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final TaskService taskService;
    private final ThreadPoolTaskExecutor singleThreadExecutor;
    private final RouteRepository routeRepository;
    private final TaskRepository taskRepository;
    private final JsonMapper mapper;
    private final NodeRepository nodeRepository;
    private final EntityMapper entityMapper;

    @Override
    public CreateRouteRs createRoute(CreateRouteRq rq) {
        try {
            TaskEntity taskEntity = taskService.createTaskWithLock(rq.getClientId(), TaskType.CALCULATE_ROUTE, rq);

            singleThreadExecutor.execute(() -> taskService.processTask(taskEntity));

            return CreateRouteRs.builder()
                    .success(true)
                    .description("Задача [" + taskEntity.getId() + "] успешно создана")
                    .build();
        } catch (TaskException e) {
            return CreateRouteRs.builder()
                    .success(false)
                    .error(ErrorDTO.builder()
                            .title(e.getMessage())
                            .code(ErrorCode.TASK_ERROR_CODE.getCode())
                            .infos(Map.of(Constant.TEXT, e.getMessage()))
                            .build())
                    .build();
        } catch (Exception e) {
            return CreateRouteRs.builder()
                    .success(false)
                    .error(ErrorDTO.builder()
                            .title("Непредвиденная ошибка")
                            .code(ErrorCode.TASK_UNEXPECTED_ERROR_CODE.getCode())
                            .infos(Map.of(Constant.TEXT, e.getMessage()))
                            .build())
                    .build();
        }
    }

    @Override
    @Transactional
    public void applyRoute(UUID id) {
        RouteEntity route = routeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Не найден маршрут с ID: " + id));

        TaskEntity taskEntity = taskRepository.findById(route.getTaskId())
                .orElseThrow(() -> new NotFoundException("Не найдена задача с ID: " + route.getTaskId()));

        if (!taskEntity.isActiveFlag()) {
            throw new TaskException("Задача [" + taskEntity.getId() + "] не активна");
        }

        List<Node> nodes;
        try {
            nodes = mapper.treeToValue(route.getRouteData(), new TypeReference<List<Node>>() {});
        } catch (Exception e) {
            throw new TaskException("Не удалось десериализовать маршрут.");
        }

        List<NodeEntity> nodeEntities = entityMapper.map(nodes);

        nodeRepository.saveAll(nodeEntities);
        routeRepository.save(route.setActiveFlag(true));
        taskRepository.save(taskEntity.setActiveFlag(false));

    }
}

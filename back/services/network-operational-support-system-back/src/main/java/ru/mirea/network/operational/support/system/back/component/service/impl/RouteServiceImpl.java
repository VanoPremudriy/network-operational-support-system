package ru.mirea.network.operational.support.system.back.component.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.back.component.service.RouteService;
import ru.mirea.network.operational.support.system.back.component.service.TaskService;
import ru.mirea.network.operational.support.system.back.dictionary.Constant;
import ru.mirea.network.operational.support.system.back.exception.TaskException;
import ru.mirea.network.operational.support.system.common.api.ErrorDTO;
import ru.mirea.network.operational.support.system.common.dictionary.ErrorCode;
import ru.mirea.network.operational.support.system.db.dictionary.TaskType;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRq;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRs;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final TaskService taskService;
    private final ThreadPoolTaskExecutor singleThreadExecutor;

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
}

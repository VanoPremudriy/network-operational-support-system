package ru.mirea.network.operational.support.system.back.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.back.exception.TaskException;
import ru.mirea.network.operational.support.system.back.service.CreateRouteService;
import ru.mirea.network.operational.support.system.back.service.TaskService;
import ru.mirea.network.operational.support.system.back.dictionary.Constant;
import ru.mirea.network.operational.support.system.common.api.ErrorDTO;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRq;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRs;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateRouteServiceImpl implements CreateRouteService {
    private final TaskService taskService;
    private final ThreadPoolTaskExecutor singleThreadExecutor;

    @Override
    public CreateRouteRs createRoute(CreateRouteRq rq) {
        try {
            TaskEntity taskEntity = taskService.createTaskWithLock(rq.getClientId());

            singleThreadExecutor.execute(() -> taskService.processTask(taskEntity));

            return CreateRouteRs.builder()
                    .success(true)
                    .build();
        } catch (TaskException e) {
            return CreateRouteRs.builder()
                    .success(false)
                    .error(ErrorDTO.builder()
                            .title(e.getMessage())
                            .code(Constant.TASK_ERROR_CODE)
                            .infos(Map.of(Constant.TEXT, e.getMessage()))
                            .build())
                    .build();
        } catch (Exception e) {
            return CreateRouteRs.builder()
                    .success(false)
                    .error(ErrorDTO.builder()
                            .title("Непредвиденная ошибка")
                            .code(Constant.UNEXPECTED_ERROR_CODE)
                            .infos(Map.of(Constant.TEXT, e.getMessage()))
                            .build())
                    .build();
        }
    }
}

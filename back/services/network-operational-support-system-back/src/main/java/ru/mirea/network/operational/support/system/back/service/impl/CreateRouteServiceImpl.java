package ru.mirea.network.operational.support.system.back.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.back.client.CalculateRouteClient;
import ru.mirea.network.operational.support.system.back.entity.TaskEntity;
import ru.mirea.network.operational.support.system.back.mapper.RootMapper;
import ru.mirea.network.operational.support.system.back.service.CreateRouteService;
import ru.mirea.network.operational.support.system.back.service.TaskService;
import ru.mirea.network.operational.support.system.common.api.ErrorDTO;
import ru.mirea.network.operational.support.system.route.api.route.calculate.CalculateRouteRs;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRq;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRs;

@Service
@RequiredArgsConstructor
public class CreateRouteServiceImpl implements CreateRouteService {
    private final CalculateRouteClient calculateRouteClient;
    private final TaskService taskService;
    private final RootMapper rootMapper;

    @Override
    public CreateRouteRs createRoute(CreateRouteRq rq) {
        try {
            TaskEntity taskEntity = taskService.createTask(rq.getClientId());

            CalculateRouteRs calculateRouteRs = calculateRouteClient.calculateRoute(rootMapper.map(rq, taskEntity));

            return CreateRouteRs.builder().build();
        } catch (Exception e) {
            return CreateRouteRs.builder()
                    .success(false)
                    .error(ErrorDTO.builder()
                            .title("Непредвиденная ошибка")
                            //.text(e.getMessage())
                            .build())
                    .build();
        }
    }
}

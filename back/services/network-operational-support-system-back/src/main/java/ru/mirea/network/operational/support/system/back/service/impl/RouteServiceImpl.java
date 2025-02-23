package ru.mirea.network.operational.support.system.back.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.back.client.CalculateRouteClient;
import ru.mirea.network.operational.support.system.back.entity.RouteEntity;
import ru.mirea.network.operational.support.system.back.entity.TaskEntity;
import ru.mirea.network.operational.support.system.back.exception.TaskException;
import ru.mirea.network.operational.support.system.back.mapper.RootMapper;
import ru.mirea.network.operational.support.system.back.repository.RouteRepository;
import ru.mirea.network.operational.support.system.back.service.RouteService;
import ru.mirea.network.operational.support.system.back.service.TaskService;
import ru.mirea.network.operational.support.system.back.util.Constant;
import ru.mirea.network.operational.support.system.common.api.ErrorDTO;
import ru.mirea.network.operational.support.system.route.api.route.calculate.CalculateRouteRs;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRq;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRs;
import ru.mirea.network.operational.support.system.route.api.route.kafka.Route;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    RouteRepository routeRepository;

    private final CalculateRouteClient calculateRouteClient;
    private final TaskService taskService;
    private final RootMapper rootMapper;

    @Override
    public CreateRouteRs createRoute(CreateRouteRq rq) {
        try {
            TaskEntity taskEntity = taskService.createTaskWithLock(rq.getClientId());

            CalculateRouteRs calculateRouteRs = calculateRouteClient.calculateRoute(rootMapper.map(rq, taskEntity));

            return CreateRouteRs.builder().build();
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

    @Override
    public RouteEntity createRoute(Route route) {
        return routeRepository.save(rootMapper.map(route));
    }
}

package ru.mirea.network.operational.support.system.back.component.service.impl;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.back.component.client.CalculateRouteClient;
import ru.mirea.network.operational.support.system.back.component.repository.RouteRepository;
import ru.mirea.network.operational.support.system.back.component.service.CalculateRouteService;
import ru.mirea.network.operational.support.system.db.entity.RouteEntity;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;
import ru.mirea.network.operational.support.system.python.api.calculate.CalculateRouteRq;
import ru.mirea.network.operational.support.system.python.api.calculate.CalculateRouteRs;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculateRouteServiceImpl implements CalculateRouteService {
    private final CalculateRouteClient calculateRouteClient;
    private final RouteRepository routeRepository;
    private final JsonMapper jsonMapper;

    @Override
    public RouteEntity calculate(TaskEntity taskEntity, CalculateRouteRq rq) {
        List<CalculateRouteRs> rs = calculateRouteClient.calculateRoute(rq);
        //TODO something

        return RouteEntity.builder()
                .price(BigDecimal.ONE)
                .taskId(taskEntity.getId())
                .clientId(taskEntity.getClientId())
                .activeFlag(false)
                .routeData(jsonMapper.valueToTree(rs)) // TODO Должен быть готовый маршрут
                .build();
    }
}

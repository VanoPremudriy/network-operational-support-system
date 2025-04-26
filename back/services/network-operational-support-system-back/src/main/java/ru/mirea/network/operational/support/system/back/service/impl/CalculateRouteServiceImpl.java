package ru.mirea.network.operational.support.system.back.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.back.client.CalculateRouteClient;
import ru.mirea.network.operational.support.system.back.service.CalculateRouteService;
import ru.mirea.network.operational.support.system.db.entity.RouteEntity;
import ru.mirea.network.operational.support.system.route.api.route.calculate.CalculateRouteRq;
import ru.mirea.network.operational.support.system.route.api.route.calculate.CalculateRouteRs;

@Service
@RequiredArgsConstructor
public class CalculateRouteServiceImpl implements CalculateRouteService {
    private final CalculateRouteClient calculateRouteClient;

    @Override
    public RouteEntity calculate(CalculateRouteRq rq) {
        CalculateRouteRs rs = calculateRouteClient.calculateRoute(rq);
        //TODO something

        return null;
    }
}

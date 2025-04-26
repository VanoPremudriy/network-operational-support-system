package ru.mirea.network.operational.support.system.back.service;

import ru.mirea.network.operational.support.system.db.entity.RouteEntity;
import ru.mirea.network.operational.support.system.route.api.route.calculate.CalculateRouteRq;

public interface CalculateRouteService {
    RouteEntity calculate(CalculateRouteRq rq);
}

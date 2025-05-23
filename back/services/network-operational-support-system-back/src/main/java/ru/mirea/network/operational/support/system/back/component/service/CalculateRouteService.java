package ru.mirea.network.operational.support.system.back.component.service;

import ru.mirea.network.operational.support.system.db.entity.RouteEntity;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;
import ru.mirea.network.operational.support.system.python.api.calculate.CalculateRouteRq;

import java.math.BigDecimal;
import java.util.Set;

public interface CalculateRouteService {
    Set<RouteEntity> calculate(TaskEntity taskEntity, CalculateRouteRq rq, BigDecimal capacity);
}

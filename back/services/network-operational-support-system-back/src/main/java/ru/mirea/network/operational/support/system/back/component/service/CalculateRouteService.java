package ru.mirea.network.operational.support.system.back.component.service;

import ru.mirea.network.operational.support.system.back.dictionary.Algorithm;
import ru.mirea.network.operational.support.system.db.entity.RouteEntity;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;

import java.math.BigDecimal;
import java.util.Set;

public interface CalculateRouteService {
    Set<RouteEntity> calculate(TaskEntity taskEntity,
                               Algorithm algorithm,
                               String startNode,
                               String endNode,
                               BigDecimal capacity);
}

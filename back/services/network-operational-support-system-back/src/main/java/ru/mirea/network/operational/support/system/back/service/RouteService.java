package ru.mirea.network.operational.support.system.back.service;

import ru.mirea.network.operational.support.system.back.entity.RouteEntity;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRq;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRs;
import ru.mirea.network.operational.support.system.route.api.route.kafka.Route;

public interface RouteService {
    RouteEntity createRoute(Route route);

    CreateRouteRs createRoute(CreateRouteRq rq);
}

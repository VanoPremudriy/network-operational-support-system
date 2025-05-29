package ru.mirea.network.operational.support.system.back.component.service;

import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRq;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRs;

import java.util.UUID;

public interface RouteService {
    CreateRouteRs createRoute(CreateRouteRq rq);

    void applyRoute(UUID id);

    void rejectTask(UUID id);
}

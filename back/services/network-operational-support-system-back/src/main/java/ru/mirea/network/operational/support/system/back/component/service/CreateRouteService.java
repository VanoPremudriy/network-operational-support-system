package ru.mirea.network.operational.support.system.back.component.service;

import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRq;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRs;

public interface CreateRouteService {
    CreateRouteRs createRoute(CreateRouteRq rq);
}

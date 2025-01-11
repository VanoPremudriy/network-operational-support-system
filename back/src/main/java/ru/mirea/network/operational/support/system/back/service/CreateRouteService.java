package ru.mirea.network.operational.support.system.back.service;

import ru.mirea.network.operational.support.system.api.create.route.CreateRouteRq;
import ru.mirea.network.operational.support.system.api.create.route.CreateRouteRs;

public interface CreateRouteService {
    CreateRouteRs createRoute(CreateRouteRq rq);
}

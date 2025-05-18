package ru.mirea.network.operational.support.system.info.service;

import ru.mirea.network.operational.support.system.info.api.route.GetRouteRs;

import java.util.UUID;

public interface RouteService {
    GetRouteRs getRoute();

    GetRouteRs getRouteById(UUID id);
}

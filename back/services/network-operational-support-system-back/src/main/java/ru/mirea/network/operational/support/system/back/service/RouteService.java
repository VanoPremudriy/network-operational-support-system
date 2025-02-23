package ru.mirea.network.operational.support.system.back.service;

import ru.mirea.network.operational.support.system.back.entity.RouteEntity;
import ru.mirea.network.operational.support.system.back.entity.TaskEntity;

public interface RouteService {
    RouteEntity createRoute(String clientId);
}

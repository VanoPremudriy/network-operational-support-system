package ru.mirea.network.operational.support.system.back.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.back.entity.RouteEntity;
import ru.mirea.network.operational.support.system.back.repository.RouteRepository;
import ru.mirea.network.operational.support.system.back.service.RouteService;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    RouteRepository routeRepository;

    @Override
    public RouteEntity createRoute(String clientId) {
        RouteEntity route = new RouteEntity();

        return routeRepository.save(route);
    }
}

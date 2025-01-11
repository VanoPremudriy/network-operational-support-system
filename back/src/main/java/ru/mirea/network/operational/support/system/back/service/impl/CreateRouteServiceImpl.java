package ru.mirea.network.operational.support.system.back.service.impl;

import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.api.create.route.CreateRouteRq;
import ru.mirea.network.operational.support.system.api.create.route.CreateRouteRs;
import ru.mirea.network.operational.support.system.back.service.CreateRouteService;

@Service
public class CreateRouteServiceImpl implements CreateRouteService {
    @Override
    public CreateRouteRs createRoute(CreateRouteRq rq) {
        return CreateRouteRs.builder().build();
    }
}

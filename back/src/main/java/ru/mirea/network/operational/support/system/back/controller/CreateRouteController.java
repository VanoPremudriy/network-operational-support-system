package ru.mirea.network.operational.support.system.back.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.network.operational.support.system.api.create.route.CreateRouteRq;
import ru.mirea.network.operational.support.system.api.create.route.CreateRouteRs;

@RestController
public class CreateRouteController {
    @PostMapping(value = "createRoute", consumes = "application/json")
    public CreateRouteRs createRoute(CreateRouteRq rq){
        return CreateRouteRs.builder().build();
    }
}

package ru.mirea.network.operational.support.system.back.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.network.operational.support.system.api.create.route.CreateRouteRq;
import ru.mirea.network.operational.support.system.api.create.route.CreateRouteRs;
import ru.mirea.network.operational.support.system.back.service.CreateRouteService;

@RestController
@RequiredArgsConstructor
public class CreateRouteController {
    private final CreateRouteService createRouteService;

    @PostMapping(value = "createRoute", consumes = "application/json")
    public ResponseEntity<CreateRouteRs> createRoute(CreateRouteRq rq) {
        return ResponseEntity.ok().body(createRouteService.createRoute(rq));
    }
}

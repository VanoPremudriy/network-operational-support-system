package ru.mirea.network.operational.support.system.back.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.network.operational.support.system.back.service.CreateRouteService;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRq;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRs;

@Validated
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class RouteController {
    private final CreateRouteService createRouteService;

    @PostMapping(value = "${controller.create-route}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateRouteRs> createRoute(@RequestBody @Valid CreateRouteRq rq) {
        return ResponseEntity.ok()
                .body(createRouteService.createRoute(rq));
    }
}

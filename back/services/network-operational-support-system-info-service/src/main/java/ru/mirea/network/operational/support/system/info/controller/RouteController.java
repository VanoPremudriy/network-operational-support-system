package ru.mirea.network.operational.support.system.info.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.network.operational.support.system.info.api.route.GetRouteRs;
import ru.mirea.network.operational.support.system.info.service.RouteService;

@Validated
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @GetMapping(value = "${controller.routes}")
    public ResponseEntity<GetRouteRs> getRoute() {
        return ResponseEntity.ok()
                .body(routeService.getRoute());
    }
}

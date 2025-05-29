package ru.mirea.network.operational.support.system.back.component.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.network.operational.support.system.back.component.service.RouteService;
import ru.mirea.network.operational.support.system.back.component.service.TaskService;
import ru.mirea.network.operational.support.system.common.api.BaseRs;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRq;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRs;

import java.util.UUID;

@Validated
@RefreshScope
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;
    private final TaskService taskService;

    @PostMapping(value = "${controller.create-route}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateRouteRs> createRoute(@RequestBody @Valid CreateRouteRq rq) {
        return ResponseEntity.ok()
                .body(routeService.createRoute(rq));
    }

    @PostMapping(value = "${controller.apply-route}")
    public BaseRs applyRoute(@PathVariable("id") UUID id) {
        routeService.applyRoute(id);

        return BaseRs.builder().success(true).build();
    }


    @PostMapping(value = "${controller.reject-task}")
    public BaseRs rejectTask(@PathVariable("id") UUID id) {
        routeService.rejectTask(id);

        return BaseRs.builder().success(true).build();
    }
}

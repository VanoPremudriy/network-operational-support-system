package ru.mirea.network.operational.support.system.info.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mirea.network.operational.support.system.info.api.route.GetRouteRs;
import ru.mirea.network.operational.support.system.info.api.task.TaskRouteRq;
import ru.mirea.network.operational.support.system.info.api.task.TaskRoutesRs;
import ru.mirea.network.operational.support.system.info.service.RouteService;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/v1/route")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @GetMapping(value = "${controller.route.get-all}")
    public ResponseEntity<GetRouteRs> getRoute() {
        return ResponseEntity.ok()
                .body(routeService.getRoute());
    }

    @GetMapping(value = "${controller.route.get-by-id}")
    public ResponseEntity<GetRouteRs> getRouteById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok()
                .body(routeService.getRouteById(id));
    }

    @PostMapping(value = "${controller.route.get-by-task}")
    public ResponseEntity<TaskRoutesRs> getRouteByTask(@RequestBody TaskRouteRq rq) {
        return ResponseEntity.ok().body(routeService.getRoutesByTask(rq));
    }
}

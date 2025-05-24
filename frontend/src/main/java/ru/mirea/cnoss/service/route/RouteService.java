package ru.mirea.cnoss.service.route;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.mirea.cnoss.service.BaseResponse;
import ru.mirea.cnoss.service.route.dto.RouteResponse;
import ru.mirea.cnoss.service.route.dto.createRoute.CreateRouteRequest;
import ru.mirea.cnoss.service.route.dto.createRoute.CreateRouteResponse;
import ru.mirea.cnoss.service.route.dto.taskroute.TaskRouteRequest;
import ru.mirea.cnoss.service.route.dto.taskroute.TaskRouteResponse;

@FeignClient(url = "http://localhost:8085", value = "route")
public interface RouteService {

    @GetMapping(value = "/network-operational-support-system-api/route")
    RouteResponse getRoute(@RequestHeader("Authorization") String authorization);

    @PostMapping(value = "/ntwrk-prtnl-spprt-sstm-mddlwr/createRoute")
    CreateRouteResponse createRoute(@RequestHeader("Authorization") String authorization,
                                    @RequestBody CreateRouteRequest rq);

    @PostMapping(value = "/route/task")
    TaskRouteResponse getTaskRoutes(@RequestHeader("Authorization") String authorization,
                                    @RequestBody TaskRouteRequest rq);

    @GetMapping(value = "/network-operational-support-system-api/route/{id}")
    RouteResponse getRouteById(@RequestHeader("Authorization") String authorization,
                               @PathVariable("id") String id);

    @PostMapping(value = "/route/apply/{id}")
    BaseResponse applyRoute(@RequestHeader("Authorization") String authorization,
                            @PathVariable("id") String id);
}
package ru.mirea.cnoss.service.route;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.mirea.cnoss.service.route.dto.RouteResponse;
import ru.mirea.cnoss.service.route.dto.createRoute.CreateRouteRequest;
import ru.mirea.cnoss.service.route.dto.createRoute.CreateRouteResponse;

@FeignClient(url = "http://localhost:8085", value = "route")
public interface RouteService {

    @GetMapping(value = "/network-operational-support-system-api/route")
    RouteResponse getRoute(@RequestHeader("Authorization") String authorization);

    @PostMapping(value = "/ntwrk-prtnl-spprt-sstm-mddlwr/createRoute")
    CreateRouteResponse createRoute(@RequestHeader("Authorization") String authorization,
                                    @RequestBody CreateRouteRequest rq);
}
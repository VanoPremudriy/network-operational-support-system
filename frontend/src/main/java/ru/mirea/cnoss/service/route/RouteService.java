package ru.mirea.cnoss.service.route;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.mirea.cnoss.service.route.dto.RouteResponse;

@FeignClient(url = "http://localhost:8085/network-operational-support-system-api", value = "route")
public interface RouteService {

    @GetMapping(value = "/route")
    RouteResponse getRoute(@RequestHeader("Authorization") String authorization);
}
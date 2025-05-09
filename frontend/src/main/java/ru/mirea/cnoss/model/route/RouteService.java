package ru.mirea.cnoss.model.route;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mirea.cnoss.model.route.dto.Route;

@FeignClient(url = "http://localhost:8085/network-operational-support-system-api", value = "route")
public interface RouteService {

    @GetMapping("/route")
    Route getRoute();
}

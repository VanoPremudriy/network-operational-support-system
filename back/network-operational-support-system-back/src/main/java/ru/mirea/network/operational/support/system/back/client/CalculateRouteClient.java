package ru.mirea.network.operational.support.system.back.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mirea.network.operational.support.system.api.calculate.route.CalculateRouteRq;
import ru.mirea.network.operational.support.system.api.calculate.route.CalculateRouteRs;

import static ru.mirea.network.operational.support.system.back.dictionary.Constant.CALCULATE_ROUTE_ENDPOINT;

@Validated
@FeignClient(value = "calculate-route", url = "${calculate.route.url}")
public interface CalculateRouteClient {
    @RequestMapping(method = RequestMethod.POST, value = CALCULATE_ROUTE_ENDPOINT)
    CalculateRouteRs calculateRoute(@RequestBody @Valid CalculateRouteRq rq);
}

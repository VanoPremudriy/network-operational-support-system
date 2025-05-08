package ru.mirea.network.operational.support.system.back.component.client;

import jakarta.validation.Valid;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mirea.network.operational.support.system.python.api.calculate.CalculateRouteRq;
import ru.mirea.network.operational.support.system.python.api.calculate.CalculateRouteRs;

@Validated
@RefreshScope
@FeignClient(value = "calculate-route", url = "${client.calculate-route.url}")
public interface CalculateRouteClient {
    @RequestMapping(method = RequestMethod.POST, value = "${client.calculate-route.endpoint}")
    CalculateRouteRs calculateRoute(@RequestBody @Valid CalculateRouteRq rq);
}

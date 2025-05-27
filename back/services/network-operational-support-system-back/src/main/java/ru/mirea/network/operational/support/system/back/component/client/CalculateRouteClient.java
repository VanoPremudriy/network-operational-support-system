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

import java.util.List;

@Validated
@RefreshScope
@FeignClient(value = "calculate-route", url = "${client.python.url}")
public interface CalculateRouteClient {
    @RequestMapping(method = RequestMethod.POST, value = "${client.python.create-route.endpoint}")
    List<CalculateRouteRs> createRoute(@RequestBody @Valid CalculateRouteRq rq);

    @RequestMapping(method = RequestMethod.POST, value = "${client.python.create-maxflow-route.endpoint}")
    List<CalculateRouteRs> createMaxflowRoute(@RequestBody @Valid CalculateRouteRq rq);
}

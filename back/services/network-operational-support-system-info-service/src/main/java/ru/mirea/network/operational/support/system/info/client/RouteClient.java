package ru.mirea.network.operational.support.system.info.client;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mirea.network.operational.support.system.python.api.matrix.GetMatrixRs;

import java.util.List;

@Validated
@RefreshScope
@FeignClient(value = "get-adjacency-matrix", url = "${client.get-adjacency-matrix.url}")
public interface RouteClient {
    @RequestMapping(method = RequestMethod.GET, value = "${client.get-adjacency-matrix.endpoint}")
    List<GetMatrixRs> getMatrix();
}

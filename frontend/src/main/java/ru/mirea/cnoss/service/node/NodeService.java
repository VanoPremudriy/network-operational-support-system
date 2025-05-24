package ru.mirea.cnoss.service.node;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.mirea.cnoss.service.node.dto.DetailedNodeResponse;


@FeignClient(url = "http://localhost:8085/", value = "nodes")
public interface NodeService {

    @GetMapping("/network-operational-support-system-api/node/{id}")
    DetailedNodeResponse getNodeById(@PathVariable("id") String id, @RequestHeader("Authorization") String authorization);
}

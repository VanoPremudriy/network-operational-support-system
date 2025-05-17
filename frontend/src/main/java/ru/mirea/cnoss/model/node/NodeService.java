package ru.mirea.cnoss.model.node;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(url = "http://localhost:8085/", value = "nodes")
public interface NodeService {

    @GetMapping("/node/{id}")
    void getNodeById(@PathVariable("id") String id, @RequestHeader("Authorization") String token);
}

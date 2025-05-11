package ru.mirea.network.operational.support.system.info.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.network.operational.support.system.info.api.node.DetailedNodeRs;
import ru.mirea.network.operational.support.system.info.api.node.NodeListRs;
import ru.mirea.network.operational.support.system.info.service.NodeService;

import java.util.UUID;

@Deprecated
@Validated
//@RestController
@RequestMapping("/v1/node")
@RequiredArgsConstructor
public class NodeController {
    private final NodeService nodeService;

    @GetMapping(value = "${controller.node.get-all}")
    public ResponseEntity<NodeListRs> getNodes() {
        return ResponseEntity.ok()
                .body(nodeService.getAll());
    }

    @GetMapping(value = "${controller.node.get-by-id}")
    public ResponseEntity<DetailedNodeRs> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok()
                .body(nodeService.getById(id));
    }
}

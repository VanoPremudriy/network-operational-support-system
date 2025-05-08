package ru.mirea.network.operational.support.system.info.service;

import ru.mirea.network.operational.support.system.info.api.node.DetailedNodeRs;
import ru.mirea.network.operational.support.system.info.api.node.NodeListRs;

import java.util.UUID;

public interface NodeService {
    NodeListRs getAll();

    DetailedNodeRs getById(UUID id);
}

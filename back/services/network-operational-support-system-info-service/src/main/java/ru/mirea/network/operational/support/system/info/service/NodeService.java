package ru.mirea.network.operational.support.system.info.service;

import ru.mirea.network.operational.support.system.info.api.info.node.DetailedNodeRs;
import ru.mirea.network.operational.support.system.info.api.info.node.NodeListRs;
import ru.mirea.network.operational.support.system.info.api.info.task.DetailedTaskRs;
import ru.mirea.network.operational.support.system.info.api.info.task.TaskListRs;

import java.util.UUID;

public interface NodeService {
    NodeListRs getAll();

    DetailedNodeRs getById(UUID id);
}

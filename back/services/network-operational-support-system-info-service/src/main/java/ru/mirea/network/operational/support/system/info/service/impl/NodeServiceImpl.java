package ru.mirea.network.operational.support.system.info.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.common.api.ErrorDTO;
import ru.mirea.network.operational.support.system.db.entity.NodeEntity;
import ru.mirea.network.operational.support.system.info.api.node.DetailedNodeRs;
import ru.mirea.network.operational.support.system.info.api.node.NodeListRs;
import ru.mirea.network.operational.support.system.info.mapper.EntityMapper;
import ru.mirea.network.operational.support.system.info.repository.NodeRepository;
import ru.mirea.network.operational.support.system.info.service.NodeService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NodeServiceImpl implements NodeService {
    private final NodeRepository nodeRepository;
    private final EntityMapper entityMapper;


    @Override
    public NodeListRs getAll() {
        List<NodeEntity> nodeList = nodeRepository.findAll();
        return NodeListRs.builder()
                .body(entityMapper.mapNode(nodeList))
                .success(true)
                .build();
    }

    @Override
    public DetailedNodeRs getById(UUID id) {
        NodeEntity nodeEntity = nodeRepository.findByNodeIdDetailed(id);
        if (nodeEntity == null) {
            return DetailedNodeRs.builder()
                    .error(ErrorDTO.builder()
                            .title("Нода [" + id + "] не найдена")
                            .build())
                    .success(false)
                    .build();
        }

        return DetailedNodeRs.builder()
                .body(entityMapper.mapDetailed(nodeEntity))
                .success(true)
                .build();
    }
}

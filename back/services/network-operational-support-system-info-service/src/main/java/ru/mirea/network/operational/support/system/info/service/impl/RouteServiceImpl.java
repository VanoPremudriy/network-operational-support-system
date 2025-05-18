package ru.mirea.network.operational.support.system.info.service.impl;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.mirea.network.operational.support.system.db.entity.NodeEntity;
import ru.mirea.network.operational.support.system.db.entity.RouteEntity;
import ru.mirea.network.operational.support.system.info.api.route.GetRouteRs;
import ru.mirea.network.operational.support.system.info.api.route.RouteEdge;
import ru.mirea.network.operational.support.system.info.api.route.RouteNode;
import ru.mirea.network.operational.support.system.info.client.RouteClient;
import ru.mirea.network.operational.support.system.info.repository.NodeRepository;
import ru.mirea.network.operational.support.system.info.repository.RouteRepository;
import ru.mirea.network.operational.support.system.info.service.RouteService;
import ru.mirea.network.operational.support.system.python.api.matrix.GetMatrixRs;
import ru.mirea.network.operational.support.system.route.api.route.common.Node;
import ru.mirea.network.operational.support.system.route.api.route.common.RouteInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteClient routeClient;
    private final JsonMapper jsonMapper;
    private final NodeRepository nodeRepository;
    private final RouteRepository routeRepository;
    private List<GetMatrixRs> cachedMatrix;

    @Override
    public GetRouteRs getRoute() {
        if (cachedMatrix == null) {
            cachedMatrix = routeClient.getMatrix();
        }

        Set<UUID> ids = new HashSet<>();
        Set<RouteNode> nodes = new HashSet<>();
        Set<RouteEdge> edges = new HashSet<>();

        for (GetMatrixRs m : cachedMatrix) {
            ids.add(m.getStartCity());
            if (!CollectionUtils.isEmpty(m.getAdjacentCities())) {
                m.getAdjacentCities().forEach(c -> {
                    ids.add(c);
                    edges.add(RouteEdge.builder()
                            .source(m.getStartCity())
                            .target(c)
                            .build());
                });
            }
        }

        List<NodeEntity> nodeEntities = nodeRepository.findByNodeIdDetailed(ids);

        for (NodeEntity nodeEntity : nodeEntities) {
            AtomicInteger equipmentAmount = new AtomicInteger();

            if (nodeEntity.getBaskets() != null) {
                nodeEntity.getBaskets().forEach(b -> {
                    equipmentAmount.incrementAndGet();
                    if (b.getBoards() != null) {
                        equipmentAmount.addAndGet(b.getBoards().size());
                    }
                });
            }

            nodes.add(RouteNode.builder()
                    .id(nodeEntity.getId())
                    .coordinates(List.of(nodeEntity.getLongitude(), nodeEntity.getLatitude()))
                    .name(nodeEntity.getName())
                    .equipmentAmount(equipmentAmount.get())
                    .build());
        }

        return GetRouteRs.builder()
                .nodes(nodes)
                .edges(edges)
                .build();
    }

    @Override
    public GetRouteRs getRouteById(UUID id) {
        RouteEntity route = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No route with id " + id));


        Set<RouteNode> nodes = new HashSet<>();
        Set<RouteEdge> edges = new HashSet<>();

        try {
            RouteInfo routeInfo = jsonMapper.treeToValue(route.getRouteData(), RouteInfo.class);
            Node previous = null;
            for (Node node : routeInfo.getNodes()) {
                if (previous != null) {
                    edges.add(RouteEdge.builder()
                            .source(previous.getId())
                            .target(node.getId())
                            .build());
                }
                nodes.add(RouteNode.builder()
                        .id(node.getId())
                        .coordinates(List.of(node.getLongitude(), node.getLatitude()))
                        .name(node.getName())
                        .build());
                previous = node;
            }
        } catch (Exception e) {
            throw new RuntimeException("Не удалось десериализовать маршрут.");
        }

        return GetRouteRs.builder()
                .nodes(nodes)
                .edges(edges)
                .build();
    }
}

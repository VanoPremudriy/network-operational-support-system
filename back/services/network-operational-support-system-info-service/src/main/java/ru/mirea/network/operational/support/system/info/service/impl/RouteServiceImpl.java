package ru.mirea.network.operational.support.system.info.service.impl;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.mirea.network.operational.support.system.db.entity.NodeEntity;
import ru.mirea.network.operational.support.system.db.entity.RouteEntity;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;
import ru.mirea.network.operational.support.system.db.entity.TaskStatus;
import ru.mirea.network.operational.support.system.info.api.route.GetRouteRs;
import ru.mirea.network.operational.support.system.info.api.route.RouteEdge;
import ru.mirea.network.operational.support.system.info.api.route.RouteNode;
import ru.mirea.network.operational.support.system.info.api.task.Route;
import ru.mirea.network.operational.support.system.info.api.task.TaskRouteRq;
import ru.mirea.network.operational.support.system.info.api.task.TaskRoutesRs;
import ru.mirea.network.operational.support.system.info.client.RouteClient;
import ru.mirea.network.operational.support.system.info.repository.NodeRepository;
import ru.mirea.network.operational.support.system.info.repository.RouteRepository;
import ru.mirea.network.operational.support.system.info.repository.TaskRepository;
import ru.mirea.network.operational.support.system.info.service.RouteService;
import ru.mirea.network.operational.support.system.python.api.matrix.GetMatrixRs;
import ru.mirea.network.operational.support.system.route.api.route.common.Node;
import ru.mirea.network.operational.support.system.route.api.route.common.RouteInfo;
import ru.mirea.network.operational.support.system.route.api.route.common.TaskInfo;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteClient routeClient;
    private final JsonMapper jsonMapper;
    private final NodeRepository nodeRepository;
    private final RouteRepository routeRepository;
    private List<GetMatrixRs> cachedMatrix;
    private final TaskRepository taskRepository;

    @Value("${controller.client.get-all.pageSize:8}")
    private final Integer pageSize;

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
                AtomicInteger equipmentAmount = new AtomicInteger();
                if (node.getBaskets() != null) {
                    node.getBaskets().forEach(b -> {
                        equipmentAmount.incrementAndGet();
                        if (b.getBoards() != null) {
                            equipmentAmount.addAndGet(b.getBoards().size());
                        }
                    });
                }

                if (previous != null) {
                    edges.add(RouteEdge.builder()
                            .source(previous.getId())
                            .target(node.getId())
                            .build());
                }
                nodes.add(RouteNode.builder()
                        .id(node.getId())
                        .equipmentAmount(equipmentAmount.get())
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

    @Override
    public TaskRoutesRs getRoutesByTask(TaskRouteRq rq) {
        Optional<TaskEntity> optionalTask = taskRepository.findById(rq.getTaskId());
        if (optionalTask.isEmpty()) {
            throw new RuntimeException("Не найдена задача для получения маршрутов");
        }
        TaskEntity task = optionalTask.get();

        List<RouteEntity> allRoutes = routeRepository.findByTaskId(rq.getTaskId());

        List<RouteEntity> filteredRoutes = allRoutes;
        if (Objects.equals(task.getStatus(), TaskStatus.SUCCESS)) {
            filteredRoutes = allRoutes.stream()
                    .filter(RouteEntity::isActiveFlag)
                    .toList();
        }

        int pageNumber = rq.getPageNumber();
        int pageSize = this.pageSize;
        int fromIndex = pageNumber * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, filteredRoutes.size());

        List<RouteEntity> pageContent = new ArrayList<>();
        if (fromIndex < filteredRoutes.size()) {
            pageContent = filteredRoutes.subList(fromIndex, toIndex);
        }

        int totalPages = (int) Math.ceil((double) filteredRoutes.size() / pageSize);

        List<Route> routes = new ArrayList<>();

        try {
            TaskInfo taskInfo = jsonMapper.treeToValue(task.getTaskData(), TaskInfo.class);
            NodeEntity destinationPoint = nodeRepository.findByNodeIdDetailed(taskInfo.getDestinationPoint());
            NodeEntity startingPoint = nodeRepository.findByNodeIdDetailed(taskInfo.getStartingPoint());

            for (RouteEntity routeEntity : pageContent) {
                RouteInfo routeInfo = jsonMapper.treeToValue(routeEntity.getRouteData(), RouteInfo.class);
                routes.add(Route.builder()
                        .id(routeEntity.getId())
                        .shifts(routeInfo.getShifts())
                        .distance(routeInfo.getDistance())
                        .destinationPoint(destinationPoint.getName())
                        .startingPoint(startingPoint.getName())
                        .capacity(taskInfo.getCapacity())
                        .price(routeEntity.getPrice())
                        .build());
            }
        } catch (Exception e) {
            throw new RuntimeException("Не удалось дессириолизовать маршруты", e);
        }

        return TaskRoutesRs.builder()
                .routes(routes)
                .numberOfPages(totalPages)
                .success(true)
                .build();
    }
}

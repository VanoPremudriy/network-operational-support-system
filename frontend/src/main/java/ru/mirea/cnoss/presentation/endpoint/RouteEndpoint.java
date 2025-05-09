package ru.mirea.cnoss.presentation.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import ru.mirea.cnoss.model.route.RouteService;
import ru.mirea.cnoss.model.route.dto.Node;
import ru.mirea.cnoss.model.route.dto.Route;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor
public class RouteEndpoint {

    private final RouteService routeService;

    public Route getRoute() {
        Route route = routeService.getRoute();
        Set<Node> nodes = route.getNodes().stream().peek(node -> {
            if (Objects.isNull(node.getEquipmentAmount())) node.setEquipmentAmount(0);
        }).collect(Collectors.toSet());

        route.setNodes(nodes);
        return route;
    }
}
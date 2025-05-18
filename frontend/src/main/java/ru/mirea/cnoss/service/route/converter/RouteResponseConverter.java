package ru.mirea.cnoss.service.route.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mirea.cnoss.service.route.dto.Edge;
import ru.mirea.cnoss.service.route.dto.Node;
import ru.mirea.cnoss.service.route.dto.Route;
import ru.mirea.cnoss.service.route.dto.RouteResponse;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RouteResponseConverter {

    private final NodeResponseConverter nodeConverter;
    private final EdgeResponseConverter edgeConverter;

    public Route convert(RouteResponse response) {
        Route route = new Route();

        route.setSuccess(response.isSuccess());
        route.setError(response.getError());
        Set<Node> nodes = response.getNodes().stream().map(nodeConverter::convert).collect(Collectors.toSet());
        Set<Edge> edges = response.getEdges().stream().map(edgeConverter::convert).collect(Collectors.toSet());

        route.setNodes(nodes);
        route.setEdges(edges);

        return route;

    }
}

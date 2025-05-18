package ru.mirea.cnoss.service.route.converter;

import org.springframework.stereotype.Component;
import ru.mirea.cnoss.service.route.dto.Edge;
import ru.mirea.cnoss.service.route.dto.EdgeResponse;

@Component
public class EdgeResponseConverter {

    public Edge convert(EdgeResponse response) {
        Edge edge = new Edge();
        edge.setSource(response.getSource());
        edge.setTarget(response.getTarget());

        return edge;
    }
}
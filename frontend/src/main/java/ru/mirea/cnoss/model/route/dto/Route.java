package ru.mirea.cnoss.model.route.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Route {
    private Set<Node> nodes;
    private Set<Edge> edges;
}
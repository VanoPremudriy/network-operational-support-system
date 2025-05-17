package ru.mirea.cnoss.model.route.dto;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.cnoss.model.BaseResponse;

import java.util.Set;

@Getter
@Setter
public class Route extends BaseResponse {
    private Set<Node> nodes;
    private Set<Edge> edges;
}
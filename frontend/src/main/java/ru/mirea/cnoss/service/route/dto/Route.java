package ru.mirea.cnoss.service.route.dto;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.cnoss.service.BaseResponse;

import java.util.Set;

@Getter
@Setter
public class Route extends BaseResponse {
    private Set<Node> nodes;
    private Set<Edge> edges;
}
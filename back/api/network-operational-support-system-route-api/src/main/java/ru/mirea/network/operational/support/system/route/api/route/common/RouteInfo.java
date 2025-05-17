package ru.mirea.network.operational.support.system.route.api.route.common;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class RouteInfo {
    private List<Node> nodes;
    private Integer distance;
    private Integer shifts;
}

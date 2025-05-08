package ru.mirea.network.operational.support.system.info.api.route;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Data
@Builder
@Jacksonized
public class GetRouteRs {
    Set<RouteNode> nodes;
    Set<RouteEdge> edges;
}

package ru.mirea.network.operational.support.system.python.api.calculate;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Data
@Builder
@Jacksonized
public class CalculateRouteRq {
    private String city1;
    private String city2;
    private Set<EdgeLoad> edgeLoad;
}

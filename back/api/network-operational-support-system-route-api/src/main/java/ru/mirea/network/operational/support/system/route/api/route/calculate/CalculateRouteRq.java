package ru.mirea.network.operational.support.system.route.api.route.calculate;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CalculateRouteRq {
    private String startingPoint;
    private String destinationPoint;
}

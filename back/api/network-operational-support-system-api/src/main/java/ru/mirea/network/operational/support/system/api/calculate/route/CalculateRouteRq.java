package ru.mirea.network.operational.support.system.api.calculate.route;

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

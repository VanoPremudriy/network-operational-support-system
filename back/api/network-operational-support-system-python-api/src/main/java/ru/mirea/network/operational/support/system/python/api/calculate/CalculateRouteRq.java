package ru.mirea.network.operational.support.system.python.api.calculate;

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

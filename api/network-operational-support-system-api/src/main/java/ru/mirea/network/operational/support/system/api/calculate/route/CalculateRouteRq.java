package ru.mirea.network.operational.support.system.api.calculate.route;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.mirea.network.operational.support.system.api.dto.PointDTO;

@Data
@Builder
@Jacksonized
public class CalculateRouteRq {
    private PointDTO startingPoint;
    private PointDTO destinationPoint;
}

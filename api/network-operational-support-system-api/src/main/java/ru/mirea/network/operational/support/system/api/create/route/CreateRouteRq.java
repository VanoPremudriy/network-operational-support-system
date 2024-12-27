package ru.mirea.network.operational.support.system.api.create.route;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.mirea.network.operational.support.system.api.dto.PointDTO;

@Data
@Builder
@Jacksonized
public class CreateRouteRq {
    private PointDTO startingPoint;
    private PointDTO destinationPoint;
}

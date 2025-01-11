package ru.mirea.network.operational.support.system.api.create.route;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CreateRouteRq {
    private String startingPoint;
    private String destinationPoint;
}

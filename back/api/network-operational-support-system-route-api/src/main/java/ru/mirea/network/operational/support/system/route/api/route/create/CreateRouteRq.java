package ru.mirea.network.operational.support.system.route.api.route.create;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CreateRouteRq {
    private String clientId;
    private String startingPoint;
    private String destinationPoint;
}

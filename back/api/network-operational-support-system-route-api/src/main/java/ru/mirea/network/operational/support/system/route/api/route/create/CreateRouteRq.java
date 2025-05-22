package ru.mirea.network.operational.support.system.route.api.route.create;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Jacksonized
public class CreateRouteRq {
    private UUID clientId;
    private String startingPoint;
    private String destinationPoint;
    private BigDecimal capacity;
    private String clientName;
}

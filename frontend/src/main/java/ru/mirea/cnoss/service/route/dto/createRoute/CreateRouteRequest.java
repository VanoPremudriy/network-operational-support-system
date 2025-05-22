package ru.mirea.cnoss.service.route.dto.createRoute;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CreateRouteRequest {
    private UUID clientId;
    private String startingPoint;
    private String destinationPoint;
    private BigDecimal capacity;
}

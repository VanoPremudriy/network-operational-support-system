package ru.mirea.network.operational.support.system.route.api.route.common;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Jacksonized
public class TaskInfo {
    private BigDecimal capacity;
    private UUID clientId;
    private UUID startingPoint;
    private UUID destinationPoint;
    private String clientName;
}

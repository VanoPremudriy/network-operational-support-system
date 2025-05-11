package ru.mirea.network.operational.support.system.python.api.calculate;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Jacksonized
public class CalculateRouteRs {
    private List<UUID> route;
    private Integer distance;
    private Integer shifts;
}

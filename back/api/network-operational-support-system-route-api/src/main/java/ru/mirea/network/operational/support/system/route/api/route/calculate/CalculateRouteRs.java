package ru.mirea.network.operational.support.system.route.api.route.calculate;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.mirea.network.operational.support.system.common.api.BodyDTO;
import ru.mirea.network.operational.support.system.common.api.ErrorDTO;

@Data
@Builder
@Jacksonized
public class CalculateRouteRs {
    private boolean success;
    private ErrorDTO error;
    private BodyDTO body;
}

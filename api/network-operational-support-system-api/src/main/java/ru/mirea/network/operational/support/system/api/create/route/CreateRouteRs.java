package ru.mirea.network.operational.support.system.api.create.route;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.mirea.network.operational.support.system.api.dto.BodyDTO;
import ru.mirea.network.operational.support.system.api.dto.ErrorDTO;

@Data
@Builder
@Jacksonized
public class CreateRouteRs {
    private boolean success;
    private ErrorDTO error;
    private BodyDTO body;
}

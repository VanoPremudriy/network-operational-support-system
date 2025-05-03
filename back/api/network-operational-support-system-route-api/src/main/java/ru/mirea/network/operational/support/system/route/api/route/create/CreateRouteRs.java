package ru.mirea.network.operational.support.system.route.api.route.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.mirea.network.operational.support.system.common.api.BaseRs;

@Data
@SuperBuilder
@Jacksonized
@EqualsAndHashCode(callSuper = true)
public class CreateRouteRs extends BaseRs {

    @Schema(description = "Описание", example = "something")
    private String description;
}

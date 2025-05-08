package ru.mirea.network.operational.support.system.info.api.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Jacksonized
public class RouteNode {
    @Schema(description = "Id ноды", example = "f0e5842f-8d2f-44fc-ac7e-3132b49db457")
    private UUID id;

    @Schema(description = "Название ноды", example = "something")
    private String name;

    @Schema(description = "Координаты узла")
    private List<BigDecimal> coordinates;

    @JsonProperty("equipment_amount")
    @Schema(description = "Количество установленного оборудования", example = "16")
    private Integer equipmentAmount;
}

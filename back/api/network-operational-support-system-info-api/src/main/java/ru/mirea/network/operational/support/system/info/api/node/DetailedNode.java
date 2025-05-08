package ru.mirea.network.operational.support.system.info.api.node;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
@Jacksonized
@EqualsAndHashCode(callSuper = true)
public class DetailedNode extends Node {
    @Schema(description = "Id ноды", example = "f0e5842f-8d2f-44fc-ac7e-3132b49db457")
    private UUID id;

    @Schema(description = "Описание ноды", example = "something")
    private String description;

    @Schema(description = "Широта", example = "12.34")
    private BigDecimal latitude;

    @Schema(description = "Долгота", example = "12.34")
    private BigDecimal longitude;

    @Schema(description = "Название ноды", example = "something")
    private String name;

    private List<Basket> baskets;
}

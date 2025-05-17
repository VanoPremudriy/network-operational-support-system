package ru.mirea.network.operational.support.system.info.api.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Jacksonized
public class Route {
    @Schema(description = "Id маршрута", example = "f0e5842f-8d2f-44fc-ac7e-3132b49db457")
    private UUID id;
    @Schema(description = "Цена маршрута", example = "2000.10")
    private BigDecimal price;

    @Schema(description = "Общее расстояние", example = "2000")
    private Integer distance;

    @Schema(description = "Количество переключений", example = "2")
    private Integer shifts;
}

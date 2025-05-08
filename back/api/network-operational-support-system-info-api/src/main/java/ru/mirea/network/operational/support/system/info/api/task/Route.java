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

    @Schema(description = "Флаг активности задачи", example = "true")
    private boolean activeFlag;

    @Schema(description = "Id клиента", example = "f0e5842f-8d2f-44fc-ac7e-3132b49db457")
    private UUID clientId;

    @Schema(description = "Id задачи", example = "f0e5842f-8d2f-44fc-ac7e-3132b49db457")
    private UUID taskId;

    @Schema(description = "Цена маршрута", example = "2000.10")
    private BigDecimal price;

    @Schema(description = "Данные маршрута", example = "{\"clientId\": \"f0e5842f-8d2f-44fc-ac7e-3132b49db457\"}")
    private String routeData;
}

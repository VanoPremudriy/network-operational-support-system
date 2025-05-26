package ru.mirea.network.operational.support.system.info.api.node;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Jacksonized
public class Port {
    @Schema(description = "Id порта", example = "f0e5842f-8d2f-44fc-ac7e-3132b49db457")
    private UUID id;

    @Schema(description = "Имя клиента", example = "Jon Doe")
    private String clientName;

    @Schema(description = "Название типа порта", example = "STM")
    private String portTypeName;

    @Schema(description = "Скорость", example = "2.0")
    private BigDecimal capacity;
}

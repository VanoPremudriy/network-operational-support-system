package ru.mirea.network.operational.support.system.info.api.node;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@Builder
@Jacksonized
public class Port {
    @Schema(description = "Id порта", example = "f0e5842f-8d2f-44fc-ac7e-3132b49db457")
    private UUID id;

    @Schema(description = "Id платы", example = "f0e5842f-8d2f-44fc-ac7e-3132b49db457")
    private UUID boardId;

    @Schema(description = "Id клиента", example = "f0e5842f-8d2f-44fc-ac7e-3132b49db457")
    private UUID clientId;

    @Schema(description = "Id связанного линейного порта", example = "f0e5842f-8d2f-44fc-ac7e-3132b49db457")
    private UUID linkToTheAssociatedLinearPort;

    @Schema(description = "Id связанного линейного порта с нижнего уровня", example = "f0e5842f-8d2f-44fc-ac7e-3132b49db457")
    private UUID linkToTheAssociatedLinearPortFromLowerLevel;

    @Schema(description = "Тип порта", example = "something")
    private UUID portTypeId;
}

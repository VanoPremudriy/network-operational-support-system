package ru.mirea.network.operational.support.system.info.api.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@SuperBuilder
@Jacksonized
public class TaskRouteRq {
    @Schema(description = "Номер страницы", example = "1")
    private Integer pageNumber;

    @Schema(description = "id задачи", example = "f0e5842f-8d2f-44fc-ac7e-3132b49db457")
    private UUID taskId;
}

package ru.mirea.network.operational.support.system.info.api.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder
@Jacksonized
public class GetAllTasksRq {
    @Schema(description = "Номер страницы", example = "1")
    private Integer pageNumber;
}

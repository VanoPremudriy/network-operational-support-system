package ru.mirea.network.operational.support.system.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class BaseRs {
    @Schema(description = "Флаг успеха", example = "true")
    private boolean success;

    @Schema(description = "Описание ошибок")
    private ErrorDTO errorDTO;
}

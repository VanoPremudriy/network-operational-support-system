package ru.mirea.cnoss.model.authorization.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Data
@Builder
@Jacksonized
public class ErrorDto {
    @Schema(description = "Код ошибки", example = "some_code")
    private String code;
    @Schema(description = "Заголовок", example = "some_title")
    private String title;
    @Schema(description = "Ошибки")
    private Map<String, String> infos;
}

package ru.mirea.cnoss.model.authorization.service.dto;

import com.vaadin.hilla.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInJwtDto {
    @Nullable
    private String token;

    @Schema(description = "Флаг успеха", example = "true")
    private boolean success;

    @Schema(description = "Описание ошибок")
    @Nullable
    private ErrorDto error;
}
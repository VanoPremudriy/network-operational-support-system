package ru.mirea.network.operational.support.system.info.api.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder
@Jacksonized
@EqualsAndHashCode(callSuper = true)
public class ClientDTOWithLogin extends ClientDTO {
    @Schema(description = "Логин клиента", example = "login")
    @NotBlank(message = "Поле логин является обязательным к заполнению")
    @Size(max = 50, message = "Допустимый размер поля логин 50 символов")
    private String login;
}

package ru.mirea.network.operational.support.system.info.api.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@SuperBuilder
@Jacksonized
@EqualsAndHashCode(callSuper = true)
public class ClientDTODetailed extends ClientDTO {
    @Schema(description = "Id клиента", example = "f0e5842f-8d2f-44fc-ac7e-3132b49db457")
    private UUID id;

    @Schema(description = "Логин клиента", example = "login")
    @NotBlank(message = "Поле логин является обязательным к заполнению")
    @Size(max = 50, message = "Допустимый размер поля логин 50 символов")
    private String login;
}

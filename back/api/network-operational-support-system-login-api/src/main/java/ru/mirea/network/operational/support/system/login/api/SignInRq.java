package ru.mirea.network.operational.support.system.login.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class SignInRq {

    @Schema(description = "Имя пользователя", example = "Jon")
    @Size(min = 1, max = 20, message = "Имя пользователя должно содержать от 1 до 20 символов")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Имя пользователя содержит недопустимые символы")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String login;

    @Schema(description = "Пароль", example = "my1secret1password")
    @Size(min = 1, max = 255, message = "Длина пароля должна быть от 8 до 255 символов")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Пароль содержит недопустимые символы")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
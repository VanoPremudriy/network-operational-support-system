package ru.mirea.network.operational.support.system.login.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на регистрацию")
public class SignUpRq {

    @Schema(description = "Имя пользователя", example = "Jon")
    @Size(min = 1, max = 20, message = "Имя пользователя должно содержать от 1 до 20 символов")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Имя пользователя содержит недопустимые символы")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String login;

    @Schema(description = "Пароль", example = "my1secret1password")
    @Size(min = 1, max = 255, message = "Длина пароля должна быть от 8 до 255 символов")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Пароль содержит недопустимые символы")
    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;

    @Schema(description = "Адрес электронной почты", example = "jondoe@gmail.com")
    @Size(min = 5, max = 50, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    //@NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @Schema(description = "Имя работника", example = "Андрей")
    @NotBlank(message = "Имя работника не может быть пустым")
    @Size(min = 1, max = 50, message = "Длина имени работника должна быть не более 255 символов")
    private String firstName;

    @Schema(description = "Фамилия работника", example = "Шурупов")
    @NotBlank(message = "Фамилия работника не может быть пустой")
    @Size(min = 1, max = 50, message = "Длина фамилии должна быть не более 255 символов")
    private String lastName;

    @Schema(description = "Отчество работника", example = "Андреевич")
    @Size(max = 50, message = "Длина отчества должна быть не более 255 символов")
    private String middleName;
}
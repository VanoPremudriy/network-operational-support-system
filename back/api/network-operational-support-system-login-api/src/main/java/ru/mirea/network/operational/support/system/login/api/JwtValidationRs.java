package ru.mirea.network.operational.support.system.login.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.mirea.network.operational.support.system.common.api.BaseRs;

@Data
@SuperBuilder
@Jacksonized
@Schema(description = "Запрос на регистрацию")
@EqualsAndHashCode(callSuper = true)
public class JwtValidationRs extends BaseRs {

    @Schema(description = "id пользователя", example = "\"c0a86465-9500-1791-8195-00c7ab0e0000\"")
    @Size(max = 50, message = "id пользователя должно содержать до 50 символов")
    @NotBlank(message = "id пользователя не может быть пустыми")
    private String id;

    @Schema(description = "Имя пользователя", example = "Jon")
    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String login;

    @Schema(description = "Адрес электронной почты", example = "jondoe@gmail.com")
    @Size(min = 5, max = 50, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @Schema(description = "Имя работника", example = "Андрей")
    @NotBlank(message = "Имя работника не может быть пустым")
    @Size(max = 50, message = "Длина пароля должна быть не более 255 символов")
    private String firstName;

    @Schema(description = "Фамилия работника", example = "Шурупов")
    @NotBlank(message = "Фамилия работника не может быть пустой")
    @Size(max = 50, message = "Длина фамилии должна быть не более 255 символов")
    private String lastName;

    @Schema(description = "Отчество работника", example = "Андреевич")
    @Size(max = 50, message = "Длина отчества должна быть не более 255 символов")
    private String middleName;
}
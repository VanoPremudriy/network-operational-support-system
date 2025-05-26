package ru.mirea.network.operational.support.system.info.api.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true) // игнорируем поле логина
public class ClientDTO {
    @JsonProperty("first_name")
    @Schema(description = "Имя клиента", example = "firstName")
    @NotBlank(message = "Поле имя является обязательным к заполнению")
    @Size(max = 50, message = "Допустимый размер поля имя 50 символов")
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z0-9]+$", message = "Имя может состоять только из букв и цифр")
    private String firstName;

    @JsonProperty("last_name")
    @Schema(description = "Фамилия клиента", example = "lastName")
    @NotBlank(message = "Поле Фамилия является обязательным к заполнению")
    @Size(max = 50, message = "Допустимый размер поля фамилия 50 символов")
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z0-9]+$", message = "Фамилия может состоять только из букв и цифр")
    private String lastName;

    @JsonProperty("middle_name")
    @Schema(description = "Отчество клиента", example = "middleName")
    @Size(max = 50, message = "Допустимый размер поля отчество 50 символов")
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z0-9]+$", message = "Отчество может состоять только из букв и цифр")
    private String middleName;

    @Schema(description = "Почта клиента", example = "hehe@hehe.ru")
    @Size(max = 50, message = "Допустимый размер поля email 50 символов")
    @Pattern(regexp = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$", message = "Некорректный формат email")
    private String email;

    @Schema(description = "Организация клиента", example = "organization")
    @Size(max = 50, message = "Допустимый размер поля организации 50 символов")
    private String organization;
}

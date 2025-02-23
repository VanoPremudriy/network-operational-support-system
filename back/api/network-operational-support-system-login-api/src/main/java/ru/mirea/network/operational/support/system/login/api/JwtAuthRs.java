package ru.mirea.network.operational.support.system.login.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.mirea.network.operational.support.system.common.api.BaseRs;

@Data
@SuperBuilder
@Jacksonized
@Schema(description = "Ответ c токеном доступа")
@EqualsAndHashCode(callSuper = true)
public class JwtAuthRs extends BaseRs {
    @Schema(description = "Токен доступа", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyMjUwNj...")
    private String token;
}

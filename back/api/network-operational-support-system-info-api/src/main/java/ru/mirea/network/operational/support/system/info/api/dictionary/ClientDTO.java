package ru.mirea.network.operational.support.system.info.api.dictionary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@SuperBuilder
@Jacksonized
public class ClientDTO {
    @Schema(description = "Идентификатор клиента", example = "f0e5842f-8d2f-44fc-ac7e-3132b49db457")
    private UUID id;

    @Schema(description = "ФИО клиента", example = "lastName")
    private String fullName;
}

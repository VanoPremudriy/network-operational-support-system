package ru.mirea.network.operational.support.system.info.api.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.Set;

@Data
@SuperBuilder
@Jacksonized
public class GetAllClientsRs {
    @Schema(description = "Номер страницы", example = "1")
    private Integer numberOfPages;

    private Set<ClientDTODetailed> clients;
}

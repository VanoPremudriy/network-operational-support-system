package ru.mirea.network.operational.support.system.info.api.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.mirea.network.operational.support.system.common.api.BaseRs;

import java.util.Set;

@Data
@SuperBuilder
@Jacksonized
@EqualsAndHashCode(callSuper = true)
public class GetAllClientsRs extends BaseRs {
    @Schema(description = "Номер страницы", example = "1")
    private Integer numberOfPages;

    private Set<ClientDTOWithId> clients;
}

package ru.mirea.network.operational.support.system.info.api.dictionary;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@SuperBuilder
@Jacksonized
public class ClientDTOList {
    private List<ClientDTO> clients;
}

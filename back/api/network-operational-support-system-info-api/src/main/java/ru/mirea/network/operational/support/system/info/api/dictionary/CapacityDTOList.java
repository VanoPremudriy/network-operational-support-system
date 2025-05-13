package ru.mirea.network.operational.support.system.info.api.dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@SuperBuilder
@Jacksonized
public class CapacityDTOList {
    @JsonProperty("port_types")
    private List<CapacityDTO> portTypes;
}

package ru.mirea.cnoss.service.dictionary.dto.capacity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CapacityDictionaryListDto {
    @JsonProperty("port_types")
    private List<CapacityDictionaryDto> portTypes;
}

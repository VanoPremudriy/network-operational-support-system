package ru.mirea.cnoss.service.dictionary.dto.capacity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CapacityDictionaryDto {
    @JsonProperty("port_types")
    private BigDecimal capacity;
}
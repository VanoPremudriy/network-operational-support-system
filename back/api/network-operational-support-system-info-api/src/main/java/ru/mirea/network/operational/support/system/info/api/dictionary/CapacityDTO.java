package ru.mirea.network.operational.support.system.info.api.dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@SuperBuilder
@Jacksonized
public class CapacityDTO {
    @JsonProperty("port_types")
    @Schema(description = "Требуемая скорость", example = "1.4")
    private BigDecimal capacity;
}

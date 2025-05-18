package ru.mirea.network.operational.support.system.route.api.route.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class BoardModel {
    private UUID id;
    private String name;
    private Integer levelNumber;
    private Integer numberOfSlots;
    private Boolean canSendToALowerLevel;
    @JsonProperty(value = "linear")
    private boolean isLinear;
    private BigDecimal price;
}

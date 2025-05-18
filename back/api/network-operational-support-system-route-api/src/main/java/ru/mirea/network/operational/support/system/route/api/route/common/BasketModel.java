package ru.mirea.network.operational.support.system.route.api.route.common;

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
public class BasketModel {
    private UUID id;

    private Integer levelNumber;

    private String name;

    private Integer numberOfSlots;

    private Integer allowedLambdaLimit;

    private BigDecimal price;
}

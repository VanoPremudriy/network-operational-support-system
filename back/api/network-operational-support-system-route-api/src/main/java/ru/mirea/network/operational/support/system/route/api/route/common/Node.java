package ru.mirea.network.operational.support.system.route.api.route.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class Node {
    private UUID id;

    private String description;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String name;

    private List<Basket> baskets;

}

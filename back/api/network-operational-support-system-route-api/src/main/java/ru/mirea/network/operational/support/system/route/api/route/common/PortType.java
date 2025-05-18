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
public class PortType {
    private UUID id;
    private BigDecimal capacity;
    private String name;
    private String numUnit;
    private BigDecimal price;
}

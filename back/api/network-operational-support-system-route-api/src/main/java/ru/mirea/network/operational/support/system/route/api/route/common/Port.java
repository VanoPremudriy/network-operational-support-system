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
public class Port {
    private UUID id;

    private UUID clientId;

    private PortType portType;

    private UUID taskId;

    private UUID linkToTheAssociatedLinearPort;

    private UUID linkToTheAssociatedLinearPortFromDifferentLevel;

    private BigDecimal capacity;

    private UUID board;

    @JsonProperty(value = "new")
    private boolean isNew;
}

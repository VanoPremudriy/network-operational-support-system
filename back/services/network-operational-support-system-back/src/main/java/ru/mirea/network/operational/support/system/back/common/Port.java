package ru.mirea.network.operational.support.system.back.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class Port {
    private UUID id;

    private UUID clientId;

    private UUID portTypeId;

    private UUID taskId;

    private UUID linkToTheAssociatedLinearPort;

    private UUID linkToTheAssociatedLinearPortFromLowerLevel;

    private UUID board;

    @JsonProperty(value = "new")
    private boolean isNew;
}

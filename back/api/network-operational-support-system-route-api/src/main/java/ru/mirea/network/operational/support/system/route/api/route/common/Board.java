package ru.mirea.network.operational.support.system.route.api.route.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class Board {
    private UUID id;

    private BoardModel boardModel;

    private UUID basket;

    private List<Port> ports;

    @JsonProperty(value = "new")
    private boolean isNew;
}

package ru.mirea.network.operational.support.system.python.api.matrix;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Jacksonized
public class GetMatrixRs {
    @JsonProperty("start_city")
    private UUID startCity;

    @JsonProperty("adjacent_cities")
    private List<UUID> adjacentCities;
}

package ru.mirea.network.operational.support.system.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class PointDTO {
    private Integer x;
    private Integer y;
}

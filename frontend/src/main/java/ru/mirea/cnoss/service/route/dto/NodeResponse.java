package ru.mirea.cnoss.service.route.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class NodeResponse {
    private UUID id;
    private String name;
    private List<BigDecimal> coordinates;
    @JsonProperty("equipment_amount")
    private Integer equipmentAmount;
}
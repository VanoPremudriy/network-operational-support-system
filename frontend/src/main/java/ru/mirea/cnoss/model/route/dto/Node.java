package ru.mirea.cnoss.model.route.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Node {
    private UUID id;
    private String name;
    private List<BigDecimal> coordinates;
    private Integer equipmentAmount;
}

package ru.mirea.cnoss.model.node.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class DetailedNode {
    private UUID id;
    private String description;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String name;
    private List<Basket> baskets;
}

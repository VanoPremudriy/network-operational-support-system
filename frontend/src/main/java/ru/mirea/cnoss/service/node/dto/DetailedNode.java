package ru.mirea.cnoss.service.node.dto;

import com.vaadin.hilla.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class DetailedNode {
    @Nullable
    private UUID id;
    @Nullable
    private String description;
    @Nullable
    private BigDecimal latitude;
    @Nullable
    private BigDecimal longitude;
    @Nullable
    private String name;
    @Nullable
    private List<Basket> baskets;
}
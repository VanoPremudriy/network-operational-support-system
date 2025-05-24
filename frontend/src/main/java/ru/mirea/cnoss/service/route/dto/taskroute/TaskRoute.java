package ru.mirea.cnoss.service.route.dto.taskroute;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class TaskRoute {
    private UUID id;
    private BigDecimal price;
    private Integer distance;
    private Integer shifts;
    private String startingPoint;
    private String destinationPoint;
    private BigDecimal capacity;
}
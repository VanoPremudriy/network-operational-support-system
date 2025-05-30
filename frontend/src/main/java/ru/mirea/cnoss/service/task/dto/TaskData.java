package ru.mirea.cnoss.service.task.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class TaskData {
    private BigDecimal capacity;
    private UUID clientId;
    private UUID startingPoint;
    private UUID destinationPoint;
    private String clientName;
    private String routeBuildingAlgorithm;
}
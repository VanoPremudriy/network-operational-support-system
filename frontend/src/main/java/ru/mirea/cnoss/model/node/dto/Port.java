package ru.mirea.cnoss.model.node.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Port {
    private UUID id;
    private UUID boardId;
    private UUID clientId;
    private UUID linkToTheAssociatedLinearPort;
    private UUID linkToTheAssociatedLinearPortFromLowerLevel;
    private UUID portTypeId;
}
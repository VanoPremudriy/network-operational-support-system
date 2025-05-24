package ru.mirea.cnoss.service.node.dto;

import com.vaadin.hilla.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class Port {
    private UUID id;
    @Nullable
    private UUID boardId;
    @Nullable
    private UUID clientId;
    @Nullable
    private UUID linkToTheAssociatedLinearPort;
    @Nullable
    private UUID linkToTheAssociatedLinearPortFromLowerLevel;
    @Nullable
    private UUID portTypeId;
    @Nullable
    private String clientName;
    @Nullable
    private BigDecimal capacity;
}
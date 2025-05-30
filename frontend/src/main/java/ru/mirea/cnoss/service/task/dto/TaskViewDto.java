package ru.mirea.cnoss.service.task.dto;

import com.vaadin.hilla.Nullable;
import lombok.Getter;
import lombok.Setter;
import ru.mirea.cnoss.service.BaseResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TaskViewDto extends BaseResponse {
    private UUID id;
    private boolean activeFlag;
    private LocalDateTime createdTime;
    @Nullable
    private LocalDateTime resolvedDate;
    private Integer executionCount;
    private String taskType;
    private String status;
    private BigDecimal capacity;
    private UUID clientId;
    private UUID startingPoint;
    private UUID destinationPoint;
    private String clientName;
    private String routeBuildingAlgorithm;
}
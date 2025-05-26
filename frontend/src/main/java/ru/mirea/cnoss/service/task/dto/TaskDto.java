package ru.mirea.cnoss.service.task.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TaskDto {
    private UUID id;
    private boolean activeFlag;
    private LocalDateTime createdTime;
    private LocalDateTime resolvedDate;
    private Integer executionCount;
    private String taskType;
    private String taskData;
    private String status;
    private String clientName;
}
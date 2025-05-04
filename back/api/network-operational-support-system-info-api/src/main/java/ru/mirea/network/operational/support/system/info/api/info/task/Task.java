package ru.mirea.network.operational.support.system.info.api.info.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder
@Jacksonized
public class Task {

    @Schema(description = "Id задачи", example = "f0e5842f-8d2f-44fc-ac7e-3132b49db457")
    private UUID id;

    @Schema(description = "Флаг активности задачи", example = "true")
    private boolean activeFlag;

    @Schema(description = "Id клиента", example = "f0e5842f-8d2f-44fc-ac7e-3132b49db457")
    private UUID clientId;

    @Schema(description = "Дата создания", example = "2025-05-03 11:27:51.176173")
    private LocalDateTime createdTime;

    @Schema(description = "Дата выполнения", example = "2025-05-03 11:27:51.176173")
    private LocalDateTime resolvedDate;

    @Schema(description = "Количество запусков задачи", example = "2")
    private Integer executionCount;

    @Schema(description = "Тип задачи", example = "something")
    private String taskType;

    @Schema(description = "Данные задачи", example = "{\"clientId\": \"f0e5842f-8d2f-44fc-ac7e-3132b49db457\"}")
    private String taskData;

}

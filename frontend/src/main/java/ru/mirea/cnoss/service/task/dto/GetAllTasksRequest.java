package ru.mirea.cnoss.service.task.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetAllTasksRequest {
    private Integer pageNumber;
}
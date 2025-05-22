package ru.mirea.cnoss.service.task.dto;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.cnoss.service.BaseResponse;

import java.util.Set;

@Getter
@Setter
public class GetAllTasksResponse extends BaseResponse {
    private Integer numberOfPages;
    private Set<TaskDto> tasks;
}
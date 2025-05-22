package ru.mirea.cnoss.service.task.dto;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.cnoss.service.BaseResponse;

import java.util.Set;

@Getter
@Setter
public class GetAllTasksViewResponse extends BaseResponse {
    private Integer numberOfPages;
    private Set<TaskViewDto> tasks;
}
package ru.mirea.cnoss.service.route.dto.taskroute;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRouteViewRequest {
    private String token;
    private String taskId;
    private Integer pageNumber;
}
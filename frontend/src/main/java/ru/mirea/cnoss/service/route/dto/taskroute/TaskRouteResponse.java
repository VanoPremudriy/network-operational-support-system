package ru.mirea.cnoss.service.route.dto.taskroute;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.cnoss.service.BaseResponse;

import java.util.List;

@Getter
@Setter
public class TaskRouteResponse extends BaseResponse {
    private Integer numberOfPages;
    private List<TaskRoute> routes;
}
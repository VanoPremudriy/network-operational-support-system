package ru.mirea.cnoss.service.route.dto.taskroute;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class TaskRouteRequest {
    private UUID taskId;
    private Integer pageNumber;
}

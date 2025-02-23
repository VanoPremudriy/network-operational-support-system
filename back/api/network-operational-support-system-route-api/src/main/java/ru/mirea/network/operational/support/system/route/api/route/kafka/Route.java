package ru.mirea.network.operational.support.system.route.api.route.kafka;

import lombok.Data;

import java.util.UUID;

@Data
public class Route {
    private String taskId;
    private UUID clientId;
    private String route;
}

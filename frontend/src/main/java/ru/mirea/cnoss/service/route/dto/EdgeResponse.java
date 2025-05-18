package ru.mirea.cnoss.service.route.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EdgeResponse {
    private UUID source;
    private UUID target;
}
package ru.mirea.cnoss.model.route.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Edge {
    private UUID source;
    private UUID target;
}
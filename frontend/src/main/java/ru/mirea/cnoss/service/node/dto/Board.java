package ru.mirea.cnoss.service.node.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Board {
    private UUID id;
    private UUID boardModelId;
    private UUID basketId;
    private String name;
    private List<Port> ports;
}

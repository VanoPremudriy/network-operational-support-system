package ru.mirea.cnoss.model.node.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

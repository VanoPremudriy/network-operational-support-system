package ru.mirea.cnoss.service.node.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Basket {
    private UUID id;
    private UUID basketModelId;
    private UUID linkedBasketId;
    private UUID nodeId;
    private String name;
    private List<Board> boards;
}

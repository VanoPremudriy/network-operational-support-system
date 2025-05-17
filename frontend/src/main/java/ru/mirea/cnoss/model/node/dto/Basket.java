package ru.mirea.cnoss.model.node.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

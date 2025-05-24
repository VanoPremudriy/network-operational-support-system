package ru.mirea.cnoss.service.node.dto;

import com.vaadin.hilla.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Basket {
    @Nullable
    private UUID id;
    @Nullable
    private UUID basketModelId;
    @Nullable
    private UUID linkedBasketId;
    @Nullable
    private UUID nodeId;
    @Nullable
    private String name;
    @Nullable
    private List<Board> boards;
}

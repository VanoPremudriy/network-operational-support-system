package ru.mirea.cnoss.service.node.dto;

import com.vaadin.hilla.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Board {
    @Nullable
    private UUID id;
    @Nullable
    private UUID boardModelId;
    @Nullable
    private UUID basketId;
    @Nullable
    private String name;
    @Nullable
    private List<Port> ports;
}

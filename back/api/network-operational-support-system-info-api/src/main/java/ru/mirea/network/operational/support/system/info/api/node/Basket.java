package ru.mirea.network.operational.support.system.info.api.node;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Jacksonized
public class Basket {
    @Schema(description = "Id корзины", example = "f0e5842f-8d2f-44fc-ac7e-3132b49db457")
    private UUID id;

    @Schema(description = "Название корзины", example = "something")
    private String name;

    private List<Board> boards;
}

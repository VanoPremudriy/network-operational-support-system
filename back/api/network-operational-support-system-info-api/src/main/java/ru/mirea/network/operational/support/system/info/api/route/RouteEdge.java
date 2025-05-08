package ru.mirea.network.operational.support.system.info.api.route;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@Builder
@Jacksonized
public class RouteEdge {
    @Schema(description = "Точка от которой идет соединение", example = "A")
    private UUID source;

    @Schema(description = "Точка к которой идет соединение", example = "B")
    private UUID target;

    public boolean equals(Object other) {
        if (other instanceof RouteEdge r) {
            return this.source.equals(r.source) && this.target.equals(r.target)
                    || this.source.equals(r.target) && this.target.equals(r.source);
        }
        return false;
    }
    public int hashCode() {
        return this.source.hashCode() ^ this.target.hashCode();
    }
}

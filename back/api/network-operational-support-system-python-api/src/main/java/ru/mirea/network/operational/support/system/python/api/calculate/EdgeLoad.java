package ru.mirea.network.operational.support.system.python.api.calculate;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@Jacksonized
@EqualsAndHashCode
public class EdgeLoad {
    private Set<UUID> edge;

    @EqualsAndHashCode.Exclude
    private BigDecimal capacity;

    public void addEdge(UUID edge) {
        if (this.edge == null) {
            this.edge = new HashSet<>();
        }
        this.edge.add(edge);
    }
}

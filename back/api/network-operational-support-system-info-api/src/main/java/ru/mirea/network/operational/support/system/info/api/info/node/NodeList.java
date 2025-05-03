package ru.mirea.network.operational.support.system.info.api.info.node;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class NodeList {
    private List<Node> nodes;
}

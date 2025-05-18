package ru.mirea.cnoss.service.route.converter;

import org.springframework.stereotype.Component;
import ru.mirea.cnoss.service.route.dto.Node;
import ru.mirea.cnoss.service.route.dto.NodeResponse;

@Component
public class NodeResponseConverter {

    public Node convert(NodeResponse response) {
        Node node = new Node();
        node.setId(response.getId());
        node.setName(response.getName());
        node.setCoordinates(response.getCoordinates());
        node.setEquipmentAmount(response.getEquipmentAmount());

        return node;
    }
}
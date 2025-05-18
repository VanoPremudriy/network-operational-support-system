package ru.mirea.network.operational.support.system.back.component.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.util.CollectionUtils;
import ru.mirea.network.operational.support.system.db.entity.BasketEntity;
import ru.mirea.network.operational.support.system.db.entity.BoardEntity;
import ru.mirea.network.operational.support.system.db.entity.NodeEntity;
import ru.mirea.network.operational.support.system.db.entity.PortEntity;
import ru.mirea.network.operational.support.system.route.api.route.common.Basket;
import ru.mirea.network.operational.support.system.route.api.route.common.Board;
import ru.mirea.network.operational.support.system.route.api.route.common.Node;
import ru.mirea.network.operational.support.system.route.api.route.common.Port;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        builder = @Builder(disableBuilder = true))
public interface EntityMapper {

    List<Node> mapEntity(List<NodeEntity> nodes);

    default UUID map(NodeEntity value) {
        return value.getId();
    }

    default UUID map(BasketEntity value) {
        return value.getId();
    }

    default UUID map(BoardEntity value) {
        return value.getId();
    }

    default UUID map(PortEntity value) {
        return value.getId();
    }

    @Mapping(target = "linkedBasket", ignore = true)
    @Mapping(target = "node", ignore = true)
    @Mapping(target = "boards", ignore = true)
    BasketEntity mapNoLinks(Basket value);

    @Mapping(target = "baskets", ignore = true)
    NodeEntity mapNoLinks(Node value);

    @Mapping(target = "linkToTheAssociatedLinearPort", ignore = true)
    @Mapping(target = "linkToTheAssociatedLinearPortFromLowerLevel", ignore = true)
    @Mapping(target = "board", ignore = true)
    PortEntity mapNoLinks(Port value);

    @Mapping(target = "ports", ignore = true)
    @Mapping(target = "basket", ignore = true)
    BoardEntity mapNoLinks(Board value);

    default List<NodeEntity> map(List<Node> nodes) {
        Map<UUID, Object> context = new HashMap<>();
        List<NodeEntity> result = new ArrayList<>();

        for (Node node : nodes) {
            result.add(map(node, context));
        }

        for (Node node : nodes) {
            if (CollectionUtils.isEmpty(node.getBaskets())) {
                continue;
            }
            for (Basket basket : node.getBaskets()) {
                BasketEntity basketEntity = (BasketEntity) context.get(basket.getId());

                basketEntity.setNode((NodeEntity) context.get(basket.getNode()));
                basketEntity.setLinkedBasket((BasketEntity) context.get(basket.getLinkedBasket()));

                if (CollectionUtils.isEmpty(basket.getBoards())) {
                    continue;
                }
                for (Board board : basket.getBoards()) {
                    BoardEntity boardEntity = (BoardEntity) context.get(board.getId());

                    boardEntity.setBasket((BasketEntity) context.get(board.getBasket()));

                    if (CollectionUtils.isEmpty(board.getPorts())) {
                        continue;
                    }
                    for (Port port : board.getPorts()) {
                        PortEntity portEntity = (PortEntity) context.get(port.getId());

                        portEntity.setLinkToTheAssociatedLinearPort(
                                (PortEntity) context.get(port.getLinkToTheAssociatedLinearPort()));
                        portEntity.setLinkToTheAssociatedLinearPortFromLowerLevel(
                                (PortEntity) context.get(port.getLinkToTheAssociatedLinearPortFromLowerLevel()));
                        portEntity.setBoard((BoardEntity) context.get(port.getBoard()));
                    }
                }
            }
        }

        return result;
    }

    default NodeEntity map(Node node, Map<UUID, Object> context) {
        NodeEntity nodeEntity = mapNoLinks(node);

        if (!CollectionUtils.isEmpty(node.getBaskets())) {
            nodeEntity.setBaskets(new HashSet<>());
            for (Basket basket : node.getBaskets()) {
                nodeEntity.getBaskets().add(map(basket, context));
            }
        }

        context.put(node.getId(), nodeEntity);

        return nodeEntity;
    }

    default BasketEntity map(Basket basket, Map<UUID, Object> context) {
        BasketEntity basketEntity = mapNoLinks(basket);

        if (!CollectionUtils.isEmpty(basket.getBoards())) {
            basketEntity.setBoards(new HashSet<>());
            for (Board board : basket.getBoards()) {
                basketEntity.getBoards().add(map(board, context));
            }
        }

        context.put(basket.getId(), basketEntity);

        return basketEntity;
    }

    default BoardEntity map(Board board, Map<UUID, Object> context) {
        BoardEntity boardEntity = mapNoLinks(board);

        if (!CollectionUtils.isEmpty(board.getPorts())) {
            boardEntity.setPorts(new HashSet<>());
            for (Port port : board.getPorts()) {
                boardEntity.getPorts().add(map(port, context));
            }
        }

        context.put(board.getId(), boardEntity);

        return boardEntity;
    }

    default PortEntity map(Port port, Map<UUID, Object> context) {
        PortEntity portEntity = mapNoLinks(port);

        context.put(port.getId(), portEntity);

        return portEntity;
    }
}

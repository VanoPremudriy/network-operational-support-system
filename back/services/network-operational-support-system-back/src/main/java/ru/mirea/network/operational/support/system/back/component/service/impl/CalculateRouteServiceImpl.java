package ru.mirea.network.operational.support.system.back.component.service.impl;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.mirea.network.operational.support.system.back.component.client.CalculateRouteClient;
import ru.mirea.network.operational.support.system.back.component.mapper.EntityMapper;
import ru.mirea.network.operational.support.system.back.component.repository.BasketModelRepository;
import ru.mirea.network.operational.support.system.back.component.repository.BoardModelRepository;
import ru.mirea.network.operational.support.system.back.component.repository.NodeRepository;
import ru.mirea.network.operational.support.system.back.component.repository.PortTypeRepository;
import ru.mirea.network.operational.support.system.back.component.service.CalculateRouteService;
import ru.mirea.network.operational.support.system.back.exception.TaskException;
import ru.mirea.network.operational.support.system.db.entity.BasketEntity;
import ru.mirea.network.operational.support.system.db.entity.BasketModelEntity;
import ru.mirea.network.operational.support.system.db.entity.BoardEntity;
import ru.mirea.network.operational.support.system.db.entity.BoardModelEntity;
import ru.mirea.network.operational.support.system.db.entity.NodeEntity;
import ru.mirea.network.operational.support.system.db.entity.PortEntity;
import ru.mirea.network.operational.support.system.db.entity.PortTypeEntity;
import ru.mirea.network.operational.support.system.db.entity.RouteEntity;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;
import ru.mirea.network.operational.support.system.python.api.calculate.CalculateRouteRq;
import ru.mirea.network.operational.support.system.python.api.calculate.CalculateRouteRs;
import ru.mirea.network.operational.support.system.route.api.route.common.RouteInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculateRouteServiceImpl implements CalculateRouteService {
    private final CalculateRouteClient calculateRouteClient;
    private final EntityMapper entityMapper;
    private final NodeRepository nodeRepository;
    private final BoardModelRepository boardModelRepository;
    private final BasketModelRepository basketModelRepository;
    private final PortTypeRepository portTypeRepository;
    private final JsonMapper jsonMapper;

    @Override
    public Set<RouteEntity> calculate(TaskEntity taskEntity, CalculateRouteRq rq, BigDecimal capacity) {
        List<CalculateRouteRs> rs = calculateRouteClient.calculateRoute(rq);

        PortTypeEntity portType = portTypeRepository.findTopByCapacityGreaterThanEqual(capacity,
                Sort.by("price").and(Sort.by("capacity"))
        );

        if (portType == null) {
            throw new TaskException("Не найдено портов с ёмкостью [" + capacity + "] ");
        }

        BoardModelEntity boardModel = boardModelRepository.findByPortTypeId(portType.getId());
        BasketModelEntity basketModel = basketModelRepository.findByBoardModelId(boardModel.getId());

        Integer baseLevel = boardModel.getLevelNumber();

        Map<Integer, List<BoardModelEntity>> boardByLevel = boardModelRepository.findByLevelNumberLessThanEqual(baseLevel)
                .stream()
                .collect(Collectors.groupingBy(BoardModelEntity::getLevelNumber));
        Map<Integer, List<BasketModelEntity>> basketsByLevel = basketModelRepository.findByLevelNumberLessThanEqual(baseLevel).stream()
                .collect(Collectors.groupingBy(BasketModelEntity::getLevelNumber));

        Set<RouteEntity> result = new HashSet<>();

        for (CalculateRouteRs routeRs : rs) {
            BigDecimal price = BigDecimal.ZERO;
            List<NodeEntity> nodes = new ArrayList<>();
            CalculateDto dto = CalculateDto.builder()
                    .price(BigDecimal.ZERO)
                    .build();
            for (int i = 0; i < routeRs.getRoute().size(); i++) {
                UUID id = routeRs.getRoute().get(i);
                NodeEntity node = nodeRepository.findByNodeIdDetailed(id);
                if (i == 0) {
                    UUID nextId = routeRs.getRoute().get(i + 1);
                    NodeEntity nextNode = nodeRepository.findByNodeIdDetailed(nextId);
                    processFirst(node, nextNode, baseLevel, capacity, dto,
                            taskEntity, basketModel, boardModel, portType, basketsByLevel, boardByLevel);
                    price = price.add(dto.getPrice());
                } else if (i == routeRs.getRoute().size() - 1) {
                    processLast(node, baseLevel, capacity, dto,
                            taskEntity, boardModel, basketModel, boardByLevel, basketsByLevel, portType);
                    price = price.add(dto.getPrice());
                } else {
                    UUID nextId = routeRs.getRoute().get(i + 1);
                    NodeEntity nextNode = nodeRepository.findByNodeIdDetailed(nextId);
                    processMiddle(capacity, node, nextNode, dto, taskEntity, boardByLevel, basketsByLevel, portType);
                    price = price.add(dto.getPrice());
                }

                nodes.add(node);
            }

            result.add(RouteEntity.builder()
                    .price(price)
                    .taskId(taskEntity.getId())
                    .clientId(taskEntity.getClient().getId())
                    .activeFlag(false)
                    .routeData(jsonMapper.valueToTree(RouteInfo.builder()
                            .nodes(entityMapper.mapEntity(nodes))
                            .shifts(routeRs.getShifts())
                            .distance(routeRs.getDistance())
                            .build()
                    ))
                    .build());
        }

        return result;
    }

    private void processFirst(NodeEntity node,
                              NodeEntity nextNode,
                              Integer currentLevel,
                              BigDecimal capacity,
                              CalculateDto dto,
                              TaskEntity taskEntity,
                              BasketModelEntity firstBasketModel,
                              BoardModelEntity firstBoardModel,
                              PortTypeEntity portType,
                              Map<Integer, List<BasketModelEntity>> basketsByLevel,
                              Map<Integer, List<BoardModelEntity>> boardByLevel) {
        BasketEntity basket = findBasket(node, nextNode, firstBasketModel);
        if (basket == null) {
            basket = createBasket(dto, node, List.of(firstBasketModel));
        }

        BoardEntity board = findBoard(basket, firstBoardModel);
        if (board == null) {
            board = createClientBoard(dto, basket, List.of(firstBoardModel));
        }

        PortEntity port = findPort(board, taskEntity, portType);

        if (port == null) {
            port = createPort(capacity, board, taskEntity, portType);
        } else {
            port = updateOrCreatePort(port, capacity, board, taskEntity, portType);
        }

        dto
                .setBasket(basket)
                .setPort(port)
                .setPrice(dto.getPrice()
                        .add(firstBoardModel.getPrice())
                        .add(firstBasketModel.getPrice())
                        .add(portType.getPrice()));

        if (currentLevel != 1) {
            while (currentLevel != 1) {

                List<BoardModelEntity> boards = boardByLevel.get(currentLevel);

                BasketEntity basketForLinear = dto.getBasket();

                BoardEntity linearBoard = findBoard(basketForLinear, boards, true);
                if (linearBoard == null) {
                    linearBoard = createLinerBoard(dto, basketForLinear, boards);
                }

                PortEntity linerPort = findPort(linearBoard, taskEntity, portType);
                if (linerPort == null) {
                    linerPort = createPort(capacity, linearBoard, taskEntity, portType);
                } else {
                    linerPort = updateOrCreatePort(linerPort, capacity, linearBoard, taskEntity, portType);
                }

                dto.getPort().setLinkToTheAssociatedLinearPort(linerPort);

                currentLevel = currentLevel - 1;

                boards = boardByLevel.get(currentLevel);
                List<BasketModelEntity> baskets = basketsByLevel.get(currentLevel);

                BasketEntity clientBasket = findBasketWithNext(node, nextNode, baskets);
                if (clientBasket == null) {
                    clientBasket = createBasket(dto, node, baskets);
                }

                BoardEntity clientBoard = findBoard(clientBasket, boards, false);
                if (clientBoard == null) {
                    clientBoard = createClientBoard(dto, clientBasket, boards);
                }

                PortEntity clientPort = findPort(clientBoard, taskEntity, portType);
                if (clientPort == null) {
                    clientPort = createPort(capacity, clientBoard, taskEntity, portType);
                } else {
                    clientPort = updateOrCreatePort(clientPort, capacity, clientBoard, taskEntity, portType);
                }

                clientPort.setLinkToTheAssociatedLinearPortFromDifferentLevel(linerPort);

                dto.setBasket(clientBasket)
                        .setPort(clientPort);
            }
        }
    }

    private void processLast(NodeEntity node,
                             Integer baseLevel,
                             BigDecimal capacity,
                             CalculateDto dto,
                             TaskEntity taskEntity,
                             BoardModelEntity lastBoardModel,
                             BasketModelEntity lastBasketModel,
                             Map<Integer, List<BoardModelEntity>> boardByLevel,
                             Map<Integer, List<BasketModelEntity>> basketsByLevel,
                             PortTypeEntity portType) {
        if (baseLevel == 1) {
            BasketEntity prevBasket = dto.getBasket();
            BasketEntity basket = findBasketWithPrev(node, prevBasket, List.of(lastBasketModel));
            if (basket == null) {
                basket = createBasket(dto, node, List.of(lastBasketModel));
            }

            BoardEntity board = findBoard(basket, lastBoardModel);
            if (board == null) {
                board = createClientBoard(dto, basket, List.of(lastBoardModel));
            }

            PortEntity port = findPort(board, taskEntity, portType);

            if (port == null) {
                createPort(capacity, board, taskEntity, portType);
            } else {
                updateOrCreatePort(port, capacity, board, taskEntity, portType);
            }

            return;
        }

        // Входящая корзина первого уровня
        List<BasketModelEntity> baskets = basketsByLevel.get(1);
        List<BoardModelEntity> boards = boardByLevel.get(1);

        BasketEntity prevBasket = dto.getBasket();

        BasketEntity inBasket = findBasketWithPrev(node, prevBasket, baskets);
        if (inBasket == null) {
            inBasket = createBasket(dto, node, baskets);
        }

        inBasket.setLinkedBasket(prevBasket);
        prevBasket.setLinkedBasket(inBasket);

        BoardEntity inBoard = findBoard(inBasket, boards, false);
        if (inBoard == null) {
            inBoard = createClientBoard(dto, inBasket, boards);
        }

        PortEntity inPort = findPort(inBoard, taskEntity, portType);

        if (inPort == null) {
            inPort = createPort(capacity, inBoard, taskEntity, portType);
        } else {
            inPort = updateOrCreatePort(inPort, capacity, inBoard, taskEntity, portType);
        }

        Integer currentLevel = 2;

        dto.setBasket(inBasket)
                .setPort(inPort);

        while (currentLevel <= baseLevel) {
            // Заполняем линейную корзину
            boards = boardByLevel.get(currentLevel);

            BasketEntity basketForLinear = dto.getBasket();

            BoardEntity linearBoard = findBoard(inBasket, boards, true);
            if (linearBoard == null) {
                linearBoard = createLinerBoard(dto, basketForLinear, boards);
            }

            PortEntity linerPort = findPort(linearBoard, taskEntity, portType);
            if (linerPort == null) {
                linerPort = createPort(capacity, linearBoard, taskEntity, portType);
            } else {
                linerPort = updateOrCreatePort(linerPort, capacity, linearBoard, taskEntity, portType);
            }

            dto.getPort().setLinkToTheAssociatedLinearPortFromDifferentLevel(linerPort);

            // Заполняем последнюю клиентскую корзину
            if (currentLevel.equals(baseLevel)) {
                BasketEntity basket = findBasket(node, lastBasketModel);
                if (basket == null) {
                    basket = createBasket(dto, node, List.of(lastBasketModel));
                }

                BoardEntity board = findBoard(basket, lastBoardModel);
                if (board == null) {
                    board = createClientBoard(dto, basket, List.of(lastBoardModel));
                }

                PortEntity port = findPort(board, taskEntity, portType);

                if (port == null) {
                    createPort(capacity, board, taskEntity, portType);
                } else {
                    updateOrCreatePort(port, capacity, board, taskEntity, portType);
                }
            } else {
                // Заполняем промежуточные клиентские корзины
                boards = boardByLevel.get(currentLevel);
                baskets = basketsByLevel.get(currentLevel);

                BasketEntity clientBasket = findBasket(node, baskets);
                if (clientBasket == null) {
                    clientBasket = createBasket(dto, node, baskets);
                }

                BoardEntity clientBoard = findBoard(clientBasket, lastBoardModel);
                if (clientBoard == null) {
                    clientBoard = createClientBoard(dto, clientBasket, boards);
                }

                PortEntity clientPort = findPort(clientBoard, taskEntity, portType);
                if (clientPort == null) {
                    clientPort = createPort(capacity, clientBoard, taskEntity, portType);
                } else {
                    clientPort = updateOrCreatePort(clientPort, capacity, clientBoard, taskEntity, portType);
                }

                clientPort.setLinkToTheAssociatedLinearPort(linerPort);

                dto.setBasket(clientBasket)
                        .setPort(clientPort);
            }

            currentLevel = currentLevel + 1;
        }
    }

    private void processMiddle(BigDecimal capacity,
                               NodeEntity node,
                               NodeEntity nextNode,
                               CalculateDto dto,
                               TaskEntity taskEntity,
                               Map<Integer, List<BoardModelEntity>> boardByLevel,
                               Map<Integer, List<BasketModelEntity>> basketsByLevel,
                               PortTypeEntity portType) {

        // Входящая корзина первого уровня
        List<BasketModelEntity> baskets = basketsByLevel.get(1);
        List<BoardModelEntity> boards = boardByLevel.get(1);

        BasketEntity prevBasket = dto.getBasket();

        BasketEntity inBasket = findBasketWithPrev(node, prevBasket, baskets);
        if (inBasket == null) {
            inBasket = createBasket(dto, node, baskets);
        }
        inBasket.setLinkedBasket(prevBasket);
        prevBasket.setLinkedBasket(inBasket);

        BoardEntity inBoard = findBoard(inBasket, boards, false);
        if (inBoard == null) {
            inBoard = createClientBoard(dto, inBasket, boards);
        }

        PortEntity inPort = findPort(inBoard, taskEntity, portType);
        if (inPort == null) {
            inPort = createPort(capacity, inBoard, taskEntity, portType);
        } else {
            inPort = updateOrCreatePort(inPort, capacity, inBoard, taskEntity, portType);
        }

        // Входящая корзина второго уровня
        baskets = basketsByLevel.get(2);
        boards = boardByLevel.get(2);

        BasketEntity inSecondBasket = findBasket(node, baskets);
        if (inSecondBasket == null) {
            inSecondBasket = createBasket(dto, node, baskets);
        }

        BoardEntity inSecondBoard = findBoard(inSecondBasket, boards, true);
        if (inSecondBoard == null) {
            inSecondBoard = createLinerBoard(dto, inSecondBasket, boards);
        }

        PortEntity inSecondPort = findPort(inSecondBoard, taskEntity, portType);
        if (inSecondPort == null) {
            inSecondPort = createPort(capacity, inSecondBoard, taskEntity, portType);
        } else {
            inSecondPort = updateOrCreatePort(inSecondPort, capacity, inSecondBoard, taskEntity, portType);
        }

        inPort.setLinkToTheAssociatedLinearPortFromDifferentLevel(inSecondPort);

        // Исходящая корзина второго уровня

        BasketEntity outSecondBasket = findBasket(node, baskets);
        if (outSecondBasket == null) {
            outSecondBasket = createBasket(dto, node, baskets);
        }

        BoardEntity outSecondBoard = findBoard(outSecondBasket, inSecondBoard, boards, true);
        if (outSecondBoard == null) {
            outSecondBoard = createLinerBoard(dto, outSecondBasket, boards);
        }

        PortEntity outSecondPort = findPort(outSecondBoard, taskEntity, portType);
        if (outSecondPort == null) {
            outSecondPort = createPort(capacity, outSecondBoard, taskEntity, portType);
        } else {
            outSecondPort = updateOrCreatePort(outSecondPort, capacity, outSecondBoard, taskEntity, portType);
        }

        outSecondPort.setLinkToTheAssociatedLinearPort(inSecondPort);
        inSecondPort.setLinkToTheAssociatedLinearPort(outSecondPort);

        // Исходящая корзина первого уровня
        baskets = basketsByLevel.get(1);
        boards = boardByLevel.get(1);

        BasketEntity outBasket = findBasketWithNext(node, nextNode, baskets);
        if (outBasket == null) {
            outBasket = createBasket(dto, node, baskets);
        }

        BoardEntity outBoard = findBoard(outBasket, boards, false);
        if (outBoard == null) {
            outBoard = createClientBoard(dto, outBasket, boards);
        }

        PortEntity outPort = findPort(outBoard, taskEntity, portType);
        if (outPort == null) {
            outPort = createPort(capacity, outBoard, taskEntity, portType);
        } else {
            outPort = updateOrCreatePort(outPort, capacity, outBoard, taskEntity, portType);
        }
        outPort.setLinkToTheAssociatedLinearPortFromDifferentLevel(outSecondPort);

        dto.setBasket(outBasket)
                .setPort(outPort);
    }

    private BasketEntity findBasket(NodeEntity node, BasketModelEntity model) {
        if (CollectionUtils.isEmpty(node.getBaskets())) {
            return null;
        }
        return node.getBaskets().stream()
                .filter(
                        b -> b.getBasketModel() == model
                                && b.getBoards().size() < b.getBasketModel().getAllowedLambdaLimit()
                )
                .findAny()
                .orElse(null);
    }


    private BasketEntity findBasket(NodeEntity node, List<BasketModelEntity> models) {
        if (CollectionUtils.isEmpty(node.getBaskets())) {
            return null;
        }
        return node.getBaskets().stream()
                .filter(
                        b -> models.contains(b.getBasketModel())
                                && b.getBoards().size() < b.getBasketModel().getAllowedLambdaLimit()
                )
                .findAny()
                .orElse(null);
    }

    private BasketEntity findBasket(NodeEntity node, NodeEntity nextNode, BasketModelEntity model) {
        if (CollectionUtils.isEmpty(node.getBaskets())) {
            return null;
        }
        return node.getBaskets().stream()
                .filter(
                        b -> b.getBasketModel() == model
                                && b.getBoards().size() < b.getBasketModel().getAllowedLambdaLimit() &&
                                (b.getLinkedBasket() == null
                                        || b.getLinkedBasket().getNode().equals(nextNode)
                                        && b.getLinkedBasket().getBoards().size() < b.getLinkedBasket().getBasketModel().getAllowedLambdaLimit())
                )
                .findAny()
                .orElse(null);
    }

    private BasketEntity findBasketWithPrev(NodeEntity node, BasketEntity prevBasket, List<BasketModelEntity> models) {
        if (CollectionUtils.isEmpty(node.getBaskets())) {
            return null;
        }
        return node.getBaskets().stream()
                .filter(b -> models.contains(b.getBasketModel())
                        && b.getBoards().size() < b.getBasketModel().getAllowedLambdaLimit() &&
                        (b.getLinkedBasket() == null || b.getLinkedBasket().equals(prevBasket))
                )
                .findAny()
                .orElse(null);
    }

    private BasketEntity findBasketWithNext(NodeEntity node, NodeEntity nextNode, List<BasketModelEntity> models) {
        if (CollectionUtils.isEmpty(node.getBaskets())) {
            return null;
        }
        return node.getBaskets().stream()
                .filter(b -> models.contains(b.getBasketModel())
                        && b.getBoards().size() < b.getBasketModel().getAllowedLambdaLimit() &&
                        (b.getLinkedBasket() == null
                                || b.getLinkedBasket().getNode().equals(nextNode)
                                && b.getLinkedBasket().getBoards().size() < b.getLinkedBasket().getBasketModel().getAllowedLambdaLimit())
                )
                .findAny()
                .orElse(null);
    }

    private BoardEntity findBoard(BasketEntity basket, List<BoardModelEntity> models, boolean isLiner) {
        if (CollectionUtils.isEmpty(basket.getBoards())) {
            return null;
        }
        return basket.getBoards().stream()
                .filter(b -> b.getBoardModel().isLinear() == isLiner
                        && models.contains(b.getBoardModel())
                        && b.getPorts().size() < b.getBoardModel().getNumberOfSlots())
                .findAny()
                .orElse(null);
    }

    private BoardEntity findBoard(BasketEntity basket, BoardEntity excluded, List<BoardModelEntity> models, boolean isLiner) {
        if (CollectionUtils.isEmpty(basket.getBoards())) {
            return null;
        }
        return basket.getBoards().stream()
                .filter(b -> b.getBoardModel().isLinear() == isLiner
                        && b != excluded
                        && models.contains(b.getBoardModel())
                        && b.getPorts().size() < b.getBoardModel().getNumberOfSlots())
                .findAny()
                .orElse(null);
    }

    private BoardEntity findBoard(BasketEntity basket, BoardModelEntity model) {
        if (CollectionUtils.isEmpty(basket.getBoards())) {
            return null;
        }
        return basket.getBoards().stream()
                .filter(b -> b.getBoardModel() == model
                        && b.getPorts().size() < b.getBoardModel().getNumberOfSlots())
                .findAny()
                .orElse(null);
    }

    private PortEntity findPort(BoardEntity board, PortTypeEntity model) {
        if (CollectionUtils.isEmpty(board.getPorts())) {
            return null;
        }
        return board.getPorts().stream()
                .filter(b -> b.getPortType() == model)
                .findAny()
                .orElse(null);
    }


    private PortEntity findPort(BoardEntity board, TaskEntity taskEntity, PortTypeEntity model) {
        if (CollectionUtils.isEmpty(board.getPorts())) {
            return null;
        }
        return board.getPorts().stream()
                .filter(b -> b.getPortType().equals(model) && b.getClientId() == taskEntity.getClient().getId())
                .findAny()
                .orElse(null);
    }

    private BasketEntity createBasket(CalculateDto dto, NodeEntity node, List<BasketModelEntity> baskets) {
        BasketModelEntity basketModel = baskets.stream()
                .min(Comparator.comparing(BasketModelEntity::getPrice))
                .orElseThrow();

        BasketEntity basket = BasketEntity.builder()
                .id(UUID.randomUUID())
                .isNew(true)
                .basketModel(basketModel)
                .node(node)
                .boards(new HashSet<>())
                .build();
        node.getBaskets().add(basket);

        dto.setPrice(dto.getPrice().add(basketModel.getPrice()));

        return basket;
    }


    private BoardEntity createLinerBoard(CalculateDto dto, BasketEntity basket, List<BoardModelEntity> models) {

        BoardModelEntity boardModel = models.stream()
                .filter(BoardModelEntity::isLinear)
                .min(Comparator.comparing(BoardModelEntity::getPrice))
                .orElseThrow();

        BoardEntity board = BoardEntity.builder()
                .id(UUID.randomUUID())
                .isNew(true)
                .boardModel(boardModel)
                .basket(basket)
                .ports(new HashSet<>())
                .build();
        basket.getBoards().add(board);

        dto.setPrice(dto.getPrice().add(boardModel.getPrice()));


        return board;
    }


    private BoardEntity createClientBoard(CalculateDto dto, BasketEntity basket, List<BoardModelEntity> models) {

        BoardModelEntity boardModel = models.stream()
                .filter(b -> !b.isLinear())
                .min(Comparator.comparing(BoardModelEntity::getPrice))
                .orElseThrow();

        BoardEntity board = BoardEntity.builder()
                .id(UUID.randomUUID())
                .isNew(true)
                .boardModel(boardModel)
                .basket(basket)
                .ports(new HashSet<>())
                .build();
        basket.getBoards().add(board);

        dto.setPrice(dto.getPrice().add(boardModel.getPrice()));

        return board;
    }

    private PortEntity updateOrCreatePort(PortEntity port, BigDecimal capacity, BoardEntity board, TaskEntity taskEntity, PortTypeEntity portType) {
        if (portType.getCapacity().compareTo(port.getCapacity().add(capacity)) <= 0) {
            port.setCapacity(port.getCapacity().add(capacity));
            return port;
        }
        BigDecimal difference = portType.getCapacity().subtract(port.getCapacity());
        port.setCapacity(portType.getCapacity());
        return createPort(capacity.subtract(difference), board, taskEntity, portType);
    }

    private PortEntity createPort(BigDecimal capacity, BoardEntity board, TaskEntity taskEntity, PortTypeEntity portType) {
        PortEntity port = PortEntity.builder()
                .id(UUID.randomUUID())
                .isNew(true)
                .clientId(taskEntity.getClient().getId())
                .taskId(taskEntity.getId())
                .portType(portType)
                .board(board)
                .capacity(capacity)
                .build();
        board.getPorts().add(port);

        return port;
    }


    @Data
    @Accessors(chain = true)
    @Builder
    static class CalculateDto {
        private BigDecimal price;
        private BasketEntity basket;
        private PortEntity port;
    }
}

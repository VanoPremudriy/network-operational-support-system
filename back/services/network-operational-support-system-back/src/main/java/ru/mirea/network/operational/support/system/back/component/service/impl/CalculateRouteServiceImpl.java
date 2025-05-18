package ru.mirea.network.operational.support.system.back.component.service.impl;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
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

        PortTypeEntity portType = portTypeRepository.findTopByCapacityOrderByPrice(capacity);

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
                    processFirst(node, baseLevel, dto, taskEntity, boardModel, basketModel, boardByLevel, basketsByLevel, portType);
                    price = price.add(dto.getPrice());
                } else if (i == routeRs.getRoute().size() - 1) {
                    processLast(node, baseLevel, dto, taskEntity, boardModel, basketModel, boardByLevel, basketsByLevel, portType);
                    price = price.add(dto.getPrice());
                } else {
                    processMiddle(node, dto, taskEntity, boardByLevel, basketsByLevel, portType);
                    price = price.add(dto.getPrice());
                }

                nodes.add(node);
            }

            result.add(RouteEntity.builder()
                    .price(price)
                    .taskId(taskEntity.getId())
                    .clientId(taskEntity.getClientId())
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
                              Integer currentLevel,
                              CalculateDto dto,
                              TaskEntity taskEntity,
                              BoardModelEntity firstBoardModel,
                              BasketModelEntity firstBasketModel,
                              Map<Integer, List<BoardModelEntity>> boardByLevel,
                              Map<Integer, List<BasketModelEntity>> basketsByLevel,
                              PortTypeEntity portType) {
        if (CollectionUtils.isEmpty(node.getBaskets())
                || node.getBaskets().stream().noneMatch(b -> b.getBasketModelId() == firstBasketModel.getId())) {
            BasketEntity basket = BasketEntity.builder()
                    .id(UUID.randomUUID())
                    .isNew(true)
                    .basketModelId(firstBasketModel.getId())
                    .name(firstBasketModel.getName())
                    .node(node)
                    .boards(new HashSet<>())
                    .build();
            node.setBaskets(new HashSet<>(Set.of(basket)));

            BoardEntity board = BoardEntity.builder()
                    .id(UUID.randomUUID())
                    .isNew(true)
                    .name(firstBoardModel.getName())
                    .boardModelId(firstBoardModel.getId())
                    .basket(basket)
                    .ports(new HashSet<>())
                    .build();
            basket.getBoards().add(board);

            PortEntity port = PortEntity.builder()
                    .id(UUID.randomUUID())
                    .isNew(true)
                    .clientId(taskEntity.getClientId())
                    .taskId(taskEntity.getId())
                    .portTypeId(portType.getId())
                    .board(board)
                    .build();
            board.getPorts().add(port);

            dto
                    .setBasket(basket)
                    .setPort(port)
                    .setPrice(dto.getPrice()
                            .add(firstBoardModel.getPrice())
                            .add(firstBasketModel.getPrice())
                            .add(portType.getPrice()));
        } else {
            //TODO
        }

        if (currentLevel != 1) {
            while (currentLevel != 1) {

                List<BoardModelEntity> boards = boardByLevel.get(currentLevel);

                BasketEntity basketForLinear = dto.getBasket();

                BoardEntity linearBoard = createLinerBoard(dto, basketForLinear, boards);

                PortEntity linerPort = createPort(dto, linearBoard, taskEntity, portType);

                dto.getPort().setLinkToTheAssociatedLinearPort(linerPort);

                currentLevel = currentLevel - 1;

                boards = boardByLevel.get(currentLevel);
                List<BasketModelEntity> baskets = basketsByLevel.get(currentLevel);

                BasketEntity clientBasket = createBasket(dto, node, baskets);
                node.getBaskets().add(clientBasket);

                BoardEntity clientBoard = createClientBoard(dto, clientBasket, boards);

                PortEntity clientPort = createPort(dto, clientBoard, taskEntity, portType);

                dto.setBasket(clientBasket)
                        .setPort(clientPort);
            }
        }
    }

    private void processLast(NodeEntity node,
                             Integer baseLevel,
                             CalculateDto dto,
                             TaskEntity taskEntity,
                             BoardModelEntity lastBoardModel,
                             BasketModelEntity lastBasketModel,
                             Map<Integer, List<BoardModelEntity>> boardByLevel,
                             Map<Integer, List<BasketModelEntity>> basketsByLevel,
                             PortTypeEntity portType) {
        // Входящая корзина первого уровня
        List<BasketModelEntity> baskets = basketsByLevel.get(1);
        List<BoardModelEntity> boards = boardByLevel.get(1);

        BasketEntity inBasket = createBasket(dto, node, baskets);

        BasketEntity prevBasket = dto.getBasket();
        inBasket.setLinkedBasket(prevBasket);
        prevBasket.setLinkedBasket(inBasket);

        BoardEntity inBoard = createClientBoard(dto, inBasket, boards);
        PortEntity inPort = createPort(dto, inBoard, taskEntity, portType);
        Integer currentLevel = 2;

        dto.setBasket(inBasket)
                .setPort(inPort);

        while (currentLevel <= baseLevel) {
            boards = boardByLevel.get(currentLevel);

            BasketEntity basketForLinear = dto.getBasket();

            BoardEntity linearBoard = createLinerBoard(dto, basketForLinear, boards);

            PortEntity linerPort = createPort(dto, linearBoard, taskEntity, portType);

            dto.getPort().setLinkToTheAssociatedLinearPort(linerPort);

            if (currentLevel.equals(baseLevel)) {
                BasketEntity basket = BasketEntity.builder()
                        .id(UUID.randomUUID())
                        .isNew(true)
                        .basketModelId(lastBasketModel.getId())
                        .name(lastBasketModel.getName())
                        .node(node)
                        .boards(new HashSet<>())
                        .build();
                node.getBaskets().add(basket);

                BoardEntity board = BoardEntity.builder()
                        .id(UUID.randomUUID())
                        .isNew(true)
                        .name(lastBoardModel.getName())
                        .boardModelId(lastBoardModel.getId())
                        .basket(basket)
                        .ports(new HashSet<>())
                        .build();
                basket.getBoards().add(board);

                PortEntity port = PortEntity.builder()
                        .id(UUID.randomUUID())
                        .isNew(true)
                        .clientId(taskEntity.getClientId())
                        .taskId(taskEntity.getId())
                        .portTypeId(portType.getId())
                        .board(board)
                        .build();
                board.getPorts().add(port);
            } else {
                boards = boardByLevel.get(currentLevel);
                baskets = basketsByLevel.get(currentLevel);

                BasketEntity clientBasket = createBasket(dto, node, baskets);
                node.getBaskets().add(clientBasket);

                BoardEntity clientBoard = createClientBoard(dto, clientBasket, boards);

                PortEntity clientPort = createPort(dto, clientBoard, taskEntity, portType);

                dto.setBasket(clientBasket)
                        .setPort(clientPort);
            }

            currentLevel = currentLevel + 1;
        }
    }

    private void processMiddle(NodeEntity node,
                               CalculateDto dto,
                               TaskEntity taskEntity,
                               Map<Integer, List<BoardModelEntity>> boardByLevel,
                               Map<Integer, List<BasketModelEntity>> basketsByLevel,
                               PortTypeEntity portType) {

        // Входящая корзина первого уровня
        List<BasketModelEntity> baskets = basketsByLevel.get(1);
        List<BoardModelEntity> boards = boardByLevel.get(1);

        BasketEntity inBasket = createBasket(dto, node, baskets);

        BasketEntity prevBasket = dto.getBasket();
        inBasket.setLinkedBasket(prevBasket);
        prevBasket.setLinkedBasket(inBasket);

        BoardEntity inBoard = createClientBoard(dto, inBasket, boards);
        PortEntity inPort = createPort(dto, inBoard, taskEntity, portType);

        // Входящая корзина второго уровня
        baskets = basketsByLevel.get(2);
        boards = boardByLevel.get(2);

        BasketEntity inSecondBasket = createBasket(dto, node, baskets);

        BoardEntity inSecondBoard = createClientBoard(dto, inSecondBasket, boards);
        PortEntity inSecondPort = createPort(dto, inSecondBoard, taskEntity, portType);

        inPort.setLinkToTheAssociatedLinearPortFromLowerLevel(inSecondPort);

        // Исходящая корзина второго уровня
        BasketEntity outSecondBasket = createBasket(dto, node, baskets);

        BoardEntity outSecondBoard = createClientBoard(dto, outSecondBasket, boards);
        PortEntity outSecondPort = createPort(dto, outSecondBoard, taskEntity, portType);

        outSecondPort.setLinkToTheAssociatedLinearPort(inSecondPort);
        inSecondPort.setLinkToTheAssociatedLinearPort(outSecondPort);

        // Исходящая корзина первого уровня
        baskets = basketsByLevel.get(1);
        boards = boardByLevel.get(1);
        BasketEntity outBasket = createBasket(dto, node, baskets);

        BoardEntity outBoard = createClientBoard(dto, outBasket, boards);
        PortEntity outPort = createPort(dto, outBoard, taskEntity, portType);

        outPort.setLinkToTheAssociatedLinearPort(outSecondPort);

        dto.setBasket(outBasket)
                .setPort(outPort);
    }

    private BasketEntity createBasket(CalculateDto dto, NodeEntity node, List<BasketModelEntity> baskets) {
        BasketModelEntity basketModel = baskets.stream()
                .min(Comparator.comparing(BasketModelEntity::getPrice))
                .orElseThrow();

        BasketEntity basket = BasketEntity.builder()
                .id(UUID.randomUUID())
                .isNew(true)
                .basketModelId(basketModel.getId())
                .name(basketModel.getName())
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
                .name(boardModel.getName())
                .boardModelId(boardModel.getId())
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
                .name(boardModel.getName())
                .boardModelId(boardModel.getId())
                .basket(basket)
                .ports(new HashSet<>())
                .build();
        basket.getBoards().add(board);

        dto.setPrice(dto.getPrice().add(boardModel.getPrice()));

        return board;
    }

    private PortEntity createPort(CalculateDto dto, BoardEntity board, TaskEntity taskEntity, PortTypeEntity portType) {
        PortEntity port = PortEntity.builder()
                .id(UUID.randomUUID())
                .isNew(true)
                .clientId(taskEntity.getClientId())
                .taskId(taskEntity.getId())
                .portTypeId(portType.getId())
                .board(board)
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

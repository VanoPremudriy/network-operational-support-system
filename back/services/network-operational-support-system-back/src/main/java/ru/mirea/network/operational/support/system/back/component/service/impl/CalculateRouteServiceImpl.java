package ru.mirea.network.operational.support.system.back.component.service.impl;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.back.component.client.CalculateRouteClient;
import ru.mirea.network.operational.support.system.back.component.repository.BasketModelRepository;
import ru.mirea.network.operational.support.system.back.component.repository.BoardAllowedPortRepository;
import ru.mirea.network.operational.support.system.back.component.repository.BoardModelRepository;
import ru.mirea.network.operational.support.system.back.component.repository.NodeRepository;
import ru.mirea.network.operational.support.system.back.component.repository.PortTypeRepository;
import ru.mirea.network.operational.support.system.back.component.repository.RouteRepository;
import ru.mirea.network.operational.support.system.back.component.service.CalculateRouteService;
import ru.mirea.network.operational.support.system.back.exception.TaskException;
import ru.mirea.network.operational.support.system.db.entity.BasketModelEntity;
import ru.mirea.network.operational.support.system.db.entity.BoardModelEntity;
import ru.mirea.network.operational.support.system.db.entity.NodeEntity;
import ru.mirea.network.operational.support.system.db.entity.PortTypeEntity;
import ru.mirea.network.operational.support.system.db.entity.RouteEntity;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;
import ru.mirea.network.operational.support.system.python.api.calculate.CalculateRouteRq;
import ru.mirea.network.operational.support.system.python.api.calculate.CalculateRouteRs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculateRouteServiceImpl implements CalculateRouteService {
    private final CalculateRouteClient calculateRouteClient;
    private final RouteRepository routeRepository;
    private final BoardAllowedPortRepository boardAllowedPortRepository;
    private final NodeRepository nodeRepository;
    private final BoardModelRepository boardModelRepository;
    private final BasketModelRepository basketModelRepository;
    private final PortTypeRepository portTypeRepository;
    private final JsonMapper jsonMapper;

    @Override
    public RouteEntity calculate(TaskEntity taskEntity, CalculateRouteRq rq, BigDecimal capacity) {
        List<CalculateRouteRs> rs = calculateRouteClient.calculateRoute(rq);

        PortTypeEntity portType = portTypeRepository.findByCapacity(capacity);

        if (portType == null) {
            throw new TaskException("Не найдено портов с ёмкостью [" + capacity + "] ");
        }

        BoardModelEntity boardModel = boardModelRepository.findByPortTypeId(portType.getId());
        BasketModelEntity basketModel = basketModelRepository.findByBoardModelId(boardModel.getId());

        Integer currentLevel = basketModel.getLevelNumber();

        Map<Integer, List<BoardModelEntity>> boardByLevel = boardModelRepository.findByLevelNumberLessThan(currentLevel)
                .stream()
                .collect(Collectors.groupingBy(BoardModelEntity::getLevelNumber));
        Map<Integer, List<BasketModelEntity>> basketsByLevel = basketModelRepository.findByLevelNumberLessThan(currentLevel).stream()
                .collect(Collectors.groupingBy(BasketModelEntity::getLevelNumber));

        List<NodeEntity> result = new ArrayList<>();

        for (CalculateRouteRs routeRs : rs) {
            for (UUID id : routeRs.getRoute()) {
                NodeEntity node = nodeRepository.findByNodeIdDetailed(id);
                if (currentLevel == 1) {

                } else {

                }
            }
        }

        return RouteEntity.builder()
                .price(BigDecimal.ONE)
                .taskId(taskEntity.getId())
                .clientId(taskEntity.getClientId())
                .activeFlag(false)
                .routeData(jsonMapper.valueToTree(result))
                .build();
    }
}

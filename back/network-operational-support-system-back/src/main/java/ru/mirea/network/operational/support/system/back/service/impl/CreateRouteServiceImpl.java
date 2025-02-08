package ru.mirea.network.operational.support.system.back.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.api.create.route.CreateRouteRq;
import ru.mirea.network.operational.support.system.api.create.route.CreateRouteRs;
import ru.mirea.network.operational.support.system.api.dto.ErrorDTO;
import ru.mirea.network.operational.support.system.back.client.CalculateRouteClient;
import ru.mirea.network.operational.support.system.back.mapper.RootMapper;
import ru.mirea.network.operational.support.system.back.service.CreateRouteService;

@Service
@RequiredArgsConstructor
public class CreateRouteServiceImpl implements CreateRouteService {
    private final CalculateRouteClient calculateRouteClient;
    private final RootMapper rootMapper;

    @Override
    public CreateRouteRs createRoute(CreateRouteRq rq) {
        try {
            return rootMapper.map(calculateRouteClient.calculateRoute(rootMapper.map(rq)));
        } catch (Exception e) {
            return CreateRouteRs.builder()
                    .success(false)
                    .error(ErrorDTO.builder()
                            .code(0)
                            .title("Непредвиденная ошибка")
                            .text(e.getMessage())
                            .build())
                    .build();
        }
    }
}

package ru.mirea.network.operational.support.system.back.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import ru.mirea.network.operational.support.system.api.calculate.route.CalculateRouteRq;
import ru.mirea.network.operational.support.system.api.calculate.route.CalculateRouteRs;
import ru.mirea.network.operational.support.system.api.create.route.CreateRouteRq;
import ru.mirea.network.operational.support.system.api.create.route.CreateRouteRs;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RootMapper {
    CalculateRouteRq map(CreateRouteRq rq);

    CreateRouteRs map(CalculateRouteRs rq);
}

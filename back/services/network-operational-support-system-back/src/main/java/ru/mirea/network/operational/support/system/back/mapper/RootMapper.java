package ru.mirea.network.operational.support.system.back.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import ru.mirea.network.operational.support.system.back.entity.TaskEntity;
import ru.mirea.network.operational.support.system.route.api.route.calculate.CalculateRouteRq;
import ru.mirea.network.operational.support.system.route.api.route.calculate.CalculateRouteRs;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRq;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRs;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RootMapper {
    CalculateRouteRq map(CreateRouteRq rq, TaskEntity taskEntity);

    CreateRouteRs map(CalculateRouteRs rq);
}

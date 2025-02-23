package ru.mirea.network.operational.support.system.back.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import ru.mirea.network.operational.support.system.back.entity.RouteEntity;
import ru.mirea.network.operational.support.system.back.entity.TaskEntity;
import ru.mirea.network.operational.support.system.route.api.route.calculate.CalculateRouteRq;
import ru.mirea.network.operational.support.system.route.api.route.calculate.CalculateRouteRs;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRq;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRs;
import ru.mirea.network.operational.support.system.route.api.route.kafka.Route;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RootMapper {
    CalculateRouteRq map(CreateRouteRq rq, TaskEntity taskEntity);

    CreateRouteRs map(CalculateRouteRs rq);

    @Mapping(target = "activeFlag", constant = "false")
    @Mapping(source = "route", target = "routeData")
    RouteEntity map(Route route);
}

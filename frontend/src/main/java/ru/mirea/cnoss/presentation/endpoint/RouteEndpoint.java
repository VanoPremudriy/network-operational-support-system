package ru.mirea.cnoss.presentation.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import ru.mirea.cnoss.presentation.utils.TokenUtils;
import ru.mirea.cnoss.service.BaseResponse;
import ru.mirea.cnoss.service.route.RouteService;
import ru.mirea.cnoss.service.route.converter.RouteResponseConverter;
import ru.mirea.cnoss.service.route.dto.Route;
import ru.mirea.cnoss.service.route.dto.RouteResponse;
import ru.mirea.cnoss.service.route.dto.createRoute.CreateRouteRequest;
import ru.mirea.cnoss.service.route.dto.createRoute.CreateRouteResponse;
import ru.mirea.cnoss.service.route.dto.taskroute.TaskRouteRequest;
import ru.mirea.cnoss.service.route.dto.taskroute.TaskRouteResponse;
import ru.mirea.cnoss.service.route.dto.taskroute.TaskRouteViewRequest;


import java.util.UUID;


@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor
public class RouteEndpoint {

    private final RouteService routeService;

    private final RouteResponseConverter routeResponseConverter;

    public Route getRoute() {
        String token = TokenUtils.getBearerTokenOrThrow();

        RouteResponse routeResponse = routeService.getRoute(token);

        return routeResponseConverter.convert(routeResponse);
    }

    public CreateRouteResponse createRoute(CreateRouteRequest request) {
        String token = TokenUtils.getBearerTokenOrThrow();
        return routeService.createRoute(token, request);
    }

    public TaskRouteResponse getTaskRoutes(TaskRouteViewRequest request) {
        String token = TokenUtils.getBearerTokenOrThrow();
        TaskRouteRequest taskRouteRequest = TaskRouteRequest.builder()
                .taskId(UUID.fromString(request.getTaskId()))
                .pageNumber(request.getPageNumber())
                .build();
        return routeService.getTaskRoutes(token, taskRouteRequest);
    }

    public Route getRouteById(String routeId) {
        String token = TokenUtils.getBearerTokenOrThrow();
        RouteResponse routeResponse = routeService.getRouteById(token, routeId);
        return routeResponseConverter.convert(routeResponse);
    }

    public BaseResponse applyRoute(String routeId) {
        String token = TokenUtils.getBearerTokenOrThrow();
        return routeService.applyRoute(token, routeId);
    }
}
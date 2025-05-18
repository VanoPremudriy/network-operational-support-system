package ru.mirea.cnoss.presentation.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import ru.mirea.cnoss.service.route.RouteService;
import ru.mirea.cnoss.service.route.converter.RouteResponseConverter;
import ru.mirea.cnoss.service.route.dto.Route;
import ru.mirea.cnoss.service.route.dto.RouteResponse;


@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor
public class RouteEndpoint {

    private final RouteService routeService;

    private final static String BEARER = "Bearer ";

    private final RouteResponseConverter routeResponseConverter;

    public Route getRoute(String userToken) {
        String token = BEARER + userToken;

        RouteResponse routeResponse = routeService.getRoute(token);

        return routeResponseConverter.convert(routeResponse);
    }
}
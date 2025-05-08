package ru.mirea.network.operational.support.system.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.mirea.network.operational.support.system.common.api.BaseRs;
import ru.mirea.network.operational.support.system.common.api.ErrorDTO;
import ru.mirea.network.operational.support.system.common.dictionary.ErrorCode;
import ru.mirea.network.operational.support.system.common.dictionary.Headers;
import ru.mirea.network.operational.support.system.gateway.exception.ResponseValidationException;
import ru.mirea.network.operational.support.system.login.api.JwtValidationRs;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RefreshScope
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final String url;

    private final String endpoint;

    private final WebClient.Builder webClientBuilder;

    private final ObjectMapper objectMapper;

    public AuthenticationGatewayFilterFactory(WebClient.Builder webClientBuilder,
                                              ObjectMapper objectMapper,
                                              @Value("${auth-server.url}") String url,
                                              @Value("${auth-server.endpoint}") String endpoint) {
        super(Config.class);
        this.url = url;
        this.webClientBuilder = webClientBuilder;
        this.endpoint = endpoint;
        this.objectMapper = objectMapper;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!config.securedEnabled) {
                return chain.filter(exchange);
            }

            String bearerToken = exchange.getRequest().getHeaders().getFirst(Headers.AUTHORIZATION.getName());

            if (bearerToken == null) {
                return onError(exchange, HttpStatus.OK, "Ошибка аутентификации", ErrorCode.EMPLOYEE_AUTH_ERROR_CODE,
                        "Нет токена авторизации");
            }

            return webClientBuilder.build().get()
                    .uri(url + endpoint)
                    .header(Headers.AUTHORIZATION.getName(), bearerToken)
                    .retrieve()
                    .bodyToMono(JwtValidationRs.class)
                    .map(response -> {
                        if (!response.isSuccess()) {
                            throw new ResponseValidationException(response);
                        }

                        exchange.getRequest().mutate().header(Headers.ID.getName(), response.getId());
                        if (response.getEmail() != null) {
                            exchange.getRequest().mutate().header(Headers.EMAIL.getName(), response.getEmail());
                        }
                        if (response.getMiddleName() != null) {
                            exchange.getRequest().mutate().header(Headers.MIDDLE_NAME.getName(), response.getMiddleName());
                        }
                        exchange.getRequest().mutate().header(Headers.FIRST_NAME.getName(), response.getFirstName());
                        exchange.getRequest().mutate().header(Headers.LAST_NAME.getName(), response.getLastName());
                        exchange.getRequest().mutate().header(Headers.LOGIN.getName(), response.getLogin());

                        return exchange;
                    })
                    .flatMap(chain::filter)
                    .onErrorResume(error -> {
                        if (error instanceof WebClientResponseException webCLientException) {
                            return onError(exchange, webCLientException.getStatusCode());
                        } else if (error instanceof ResponseValidationException responseValidationException) {
                            return onError(exchange, HttpStatus.OK, responseValidationException.getResponse());
                        }

                        return onError(exchange, HttpStatus.BAD_GATEWAY);
                    });
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatusCode statusCode, String title, ErrorCode code, String errorMessage) {
        return onError(exchange, statusCode, BaseRs.builder()
                .success(false)
                .error(ErrorDTO.builder()
                        .title(title)
                        .code(code.getCode())
                        .infos(Map.of("text", errorMessage))
                        .build())
                .build());
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatusCode statusCode, BaseRs data) {
        ServerHttpResponse response = exchange.getResponse();
        try {
            DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
            byte[] byteData = objectMapper.writeValueAsBytes(data);
            response.setStatusCode(statusCode);

            return response.writeWith(Mono.just(byteData).map(dataBufferFactory::wrap));
        } catch (JsonProcessingException e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("Не удалось сериализовать объект", e);
            return response.setComplete();
        }
    }


    private Mono<Void> onError(ServerWebExchange exchange, HttpStatusCode statusCode) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(statusCode);
        return response.setComplete();
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("securedEnabled");
    }

    @Data
    public static class Config {
        private boolean securedEnabled;
    }
}

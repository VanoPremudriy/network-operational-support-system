package ru.mirea.network.operational.support.system.gateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.mirea.network.operational.support.system.api.login.JwtValidationResponse;
import ru.mirea.network.operational.support.system.gateway.dictionary.Constant;

import java.util.List;

@Component
@Slf4j
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final String url;

    private final String endpoint;

    private final WebClient.Builder webClientBuilder;

    public AuthenticationGatewayFilterFactory(WebClient.Builder webClientBuilder,
                                              @Value("${auth-server.url}") String url,
                                              @Value("${auth-server.endpoint}") String endpoint) {
        super(Config.class);
        this.url = url;
        this.webClientBuilder = webClientBuilder;
        this.endpoint = endpoint;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info(String.valueOf(config.securedEnabled));
            if (!config.securedEnabled) {
                return chain.filter(exchange);
            }

            String bearerToken = exchange.getRequest().getHeaders().getFirst(Constant.HEADER_AUTHORIZATION);

            if (bearerToken == null) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            return webClientBuilder.build().get()
                    .uri(url + endpoint)
                    .header(Constant.HEADER_AUTHORIZATION, bearerToken)
                    .retrieve()
                    .bodyToMono(JwtValidationResponse.class)
                    .map(response -> {
                        exchange.getRequest().mutate().header("Id", response.getId());
                        exchange.getRequest().mutate().header("Email", response.getEmail());
                        exchange.getRequest().mutate().header("FirstName", response.getFirstName());
                        exchange.getRequest().mutate().header("LastName", response.getLastName());
                        exchange.getRequest().mutate().header("MiddleName", response.getMiddleName());

                        return exchange;
                    }).flatMap(chain::filter)
                    .onErrorResume(error -> {
                        if (error instanceof WebClientResponseException webCLientException) {
                            return onError(exchange, webCLientException.getStatusCode());
                        }

                        return onError(exchange, HttpStatus.BAD_GATEWAY);
                    });
        };
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

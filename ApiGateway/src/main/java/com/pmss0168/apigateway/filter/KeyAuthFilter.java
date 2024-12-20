package com.pmss0168.apigateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

@Component
public class KeyAuthFilter extends AbstractGatewayFilterFactory<KeyAuthFilter.Config> {
    @Value("${auth-key}")
    private String authKey;

    public KeyAuthFilter() {
        super(Config.class);
    }

    static class Config {

    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) ->{
            if(!exchange.getRequest().getHeaders().containsKey("Auth-Key")) {
                return handleFilterException(exchange, "Missing authorization", HttpStatus.UNAUTHORIZED);
//                throw new RuntimeException("Missing authorization information");
            }
            String authKey = exchange.getRequest().getHeaders().get("Auth-Key").getFirst();
            if(authKey == null || authKey.isEmpty() || !authKey.equals(this.authKey)) {
                return handleFilterException(exchange, "Invalid authorization", HttpStatus.FORBIDDEN);
//                throw new RuntimeException("Invalid authorization information");
            }
            ServerHttpRequest request = exchange.getRequest();
            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    private Mono<Void> handleFilterException(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String errorResponse = String.format(
                "{\"timestamp\": \"%s\", \"status\": \"%d\", \"error\": \"%s\", \"path\": \"%s\"}",
                ZonedDateTime.now().toString(), status.value(), message, exchange.getRequest().getPath()
        );
        return response.writeWith(Mono.just(response.bufferFactory().wrap(errorResponse.getBytes())));
    }
}

package com.myKcc.com.API_Gateway_Service.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AddCustomHeaderFilter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Add the custom header "X-Requested-By: MyApiParish"
        exchange.getRequest().mutate()
                .header("X-Requested-By", "MyApiParish")
                .build();

        // Continue with the filter chain
        return chain.filter(exchange);
    }
}

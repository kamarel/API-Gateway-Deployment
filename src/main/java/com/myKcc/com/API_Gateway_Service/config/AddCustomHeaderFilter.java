package com.myKcc.com.API_Gateway_Service.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AddCustomHeaderFilter extends AbstractGatewayFilterFactory<AddCustomHeaderFilter.Config> {

    public static class Config {
        // Add configuration options if necessary
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Add the custom header "X-Requested-By: MyApiParish"
            exchange.getRequest().mutate()
                    .header("X-Requested-By", "MyApiParish")
                    .build();

            // Continue with the filter chain
            return chain.filter(exchange);
        };
    }
}

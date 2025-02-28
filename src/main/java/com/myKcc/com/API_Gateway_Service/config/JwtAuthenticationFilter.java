package com.myKcc.com.API_Gateway_Service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Autowired
    private JwtTokenProvider tokenProvider;

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();

            // Allow access to the login routes
            if (path.startsWith("/api/login") || path.startsWith("/api/login/users") || path.startsWith("/api/login/users/delete")) {
                return chain.filter(exchange);
            }

            // Extract the token from the Authorization header
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");

            if (token == null || !token.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            token = token.substring(7); // Remove "Bearer " prefix

            // Log the token for debugging
            System.out.println("Received Token: " + token);

            // Validate token if it's present and correctly formatted
            if (tokenProvider.validateToken(token)) {
                return chain.filter(exchange); // Token is valid, proceed with the request
            }

            // If no valid token, reject the request
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        };
    }


    public static class Config {
        // Add configuration properties here if necessary
    }
}

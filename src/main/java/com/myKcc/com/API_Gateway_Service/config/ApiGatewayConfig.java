package com.myKcc.com.API_Gateway_Service.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    private final AddCustomHeaderFilter addCustomHeaderFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;  // Inject JwtAuthenticationFilter

    // Constructor injection for both filters
    public ApiGatewayConfig(AddCustomHeaderFilter addCustomHeaderFilter, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.addCustomHeaderFilter = addCustomHeaderFilter;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Existing Members Service route
                .route("members-service", r -> r.path("/api/v1/members/**")
                        .filters(f -> f.filter(addCustomHeaderFilter))  // Use the injected custom filter here
                        .uri("https://strong-alignment-production.up.railway.app"))  // Your actual MEMBERS-SERVICE URI

                // New Documents Service route
                .route("documents-service", r -> r.path("/api/files/**")
                        .filters(f -> f
                                .filter((GatewayFilter) jwtAuthenticationFilter)  // Use the injected JwtAuthenticationFilter
                                .addRequestHeader("X-Requested-By", "3H#7D*faG&L^2z!9Xy@uKp6T")  // Add custom header
                        )
                        .uri("http://document-service-production-8e33.up.railway.app"))  // Your actual DOCUMENTS-SERVICE URI

                .build();
    }
}

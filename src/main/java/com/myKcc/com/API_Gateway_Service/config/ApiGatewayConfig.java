package com.myKcc.com.API_Gateway_Service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    private final AddCustomHeaderFilter addCustomHeaderFilter;  // Injecting the custom filter

    // Constructor injection of the custom filter
    public ApiGatewayConfig(AddCustomHeaderFilter addCustomHeaderFilter) {
        this.addCustomHeaderFilter = addCustomHeaderFilter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("members-service", r -> r.path("/api/v1/members/**")
                        .filters(f -> f.filter(addCustomHeaderFilter))  // Use the injected custom filter here
                        .uri("https://strong-alignment-production.up.railway.app"))  // Your actual MEMBERS-SERVICE URI

                .build();
    }


}

package com.chathura.lapmart.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-service", r -> r.path("/api/v1/products/**")
                        .uri("lb://PRODUCT-SERVICE-API"))

                .route("order-service", r -> r.path("/api/v1/orders/**")
                        .uri("lb://ORDER-SERVICE-API"))

                .build();
    }
}
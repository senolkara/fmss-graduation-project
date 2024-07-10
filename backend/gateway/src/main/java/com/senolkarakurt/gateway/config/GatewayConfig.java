package com.senolkarakurt.gateway.config;

import com.senolkarakurt.gateway.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    private final JwtAuthenticationFilter filter;

    public GatewayConfig(JwtAuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/v1/auth/**")
                        .uri("lb://USER-SERVICE"))

                .route("user-service", r -> r.path("/api/v1/users/**")
                        .uri("lb://USER-SERVICE"))

                .route("customer-service", r -> r.path("/api/v1/customers/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://CUSTOMER-SERVICE"))

                .route("notification-service", r -> r.path("/api/v1/notifications/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://NOTIFICATION-SERVICE"))

                .route("advertisement-service", r -> r.path("/api/v1/advertisements/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://ADVERTISEMENT-SERVICE"))

                .route("order-service", r -> r.path("/api/v1/orders/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://ORDER-SERVICE"))

                .route("purchase-service", r -> r.path("/api/v1/purchases/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://PURCHASE-SERVICE"))

                .route("cpackage-service", r -> r.path("/api/v1/packages/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://CPACKAGE-SERVICE"))
                .build();
    }
}

package com.fnvls.apigateway.impl.config;

import com.fnvls.apigateway.impl.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth", r -> r.path("/auth/**").filters(f -> f.filter(filter)).uri("lb://user-service"))
                .route("user", r -> r.path("/user/**").filters(f -> f.filter(filter)).uri("lb://user-service"))
                .route("users", r -> r.path("/users/**").filters(f -> f.filter(filter)).uri("lb://user-service"))
                .route("training", r -> r.path("/training/**").filters(f -> f.filter(filter)).uri("lb://training-service"))
                .route("trainings", r -> r.path("/trainings/**").filters(f -> f.filter(filter)).uri("lb://training-service"))
                .route("post", r -> r.path("/post/**").filters(f -> f.filter(filter)).uri("lb://post-service"))
                .route("posts", r -> r.path("/posts/**").filters(f -> f.filter(filter)).uri("lb://post-service"))
                .route("category", r -> r.path("/category/**").filters(f -> f.filter(filter)).uri("lb://post-service"))
                .route("categories", r -> r.path("/categories/**").filters(f -> f.filter(filter)).uri("lb://post-service"))
                .route("logs", r -> r.path("/logs/**").filters(f -> f.filter(filter)).uri("lb://log-service"))
                .build();
    }
}

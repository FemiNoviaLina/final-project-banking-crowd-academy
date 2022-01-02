package com.fnvls.apigateway.impl.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnvls.apigateway.impl.exception.JwtTokenMalformedException;
import com.fnvls.apigateway.impl.exception.JwtTokenMissingException;
import com.fnvls.apigateway.impl.exceptionbody.ExceptionBody;
import com.fnvls.apigateway.impl.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();

        final List<String> apiEndpoints = List.of("/auth/session", "/auth/user");

        Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream().noneMatch(uri -> r.getURI().getPath().contains(uri));

        if(isApiSecured.test(request)) {
            if(!request.getHeaders().containsKey("Authorization")) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                ExceptionBody body = new ExceptionBody("Missing Authorization Header");
                byte[] responseBody = objectMapper.writeValueAsBytes(body);
                return response.writeWith(Flux.just(responseBody).map(r -> response.bufferFactory().wrap(r)));
            }

            final String token = request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);

            try {
                jwtUtil.validateToken(token);
            } catch (JwtTokenMalformedException | JwtTokenMissingException e) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.BAD_REQUEST);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                ExceptionBody body = new ExceptionBody(e.getMessage());
                byte[] responseBody = objectMapper.writeValueAsBytes(body);
                return response.writeWith(Flux.just(responseBody).map(r -> response.bufferFactory().wrap(r)));
            }

            Claims claims = jwtUtil.getClaims(token);
            exchange.getRequest().mutate().header("sub", claims.get("sub").toString()).build();
            exchange.getRequest().mutate().header("email", claims.get("email").toString()).build();
            exchange.getRequest().mutate().header("role", claims.get("role").toString()).build();
        }
        return chain.filter(exchange);
    }
}
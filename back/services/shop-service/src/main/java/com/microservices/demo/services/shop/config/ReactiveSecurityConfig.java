package com.microservices.demo.services.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class ReactiveSecurityConfig {

    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf()
                .disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, "/shop/allUsers").hasAnyRole("USER", "ADMIN")
                .pathMatchers(HttpMethod.GET, "/shop/createProduct*", "/shop/createProduct/*",
                        "/shop/createProduct/**").hasRole("ADMIN")
                .pathMatchers("/**").authenticated()
                .and()
                .httpBasic()
                .and()
                .build();
    }
}


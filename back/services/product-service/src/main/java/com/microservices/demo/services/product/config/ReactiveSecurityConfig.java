package com.microservices.demo.services.product.config;

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
                .pathMatchers(HttpMethod.GET, "/product/createProduct*", "/product/createProduct/*",
                        "/product/createProduct/**").hasRole("ADMIN")
                .pathMatchers(HttpMethod.GET,"/product/deleteById*", "/product/deleteById/*",
                        "/product/deleteById/**").hasRole("ADMIN")
                .pathMatchers(HttpMethod.GET,"/product/findByProductId*", "/product/findByProductId/*",
                        "/product/findByProductId/**").hasRole("ADMIN")
                .pathMatchers("/**").authenticated()
                .and()
                .httpBasic()
                .and()
                .build();
    }
}


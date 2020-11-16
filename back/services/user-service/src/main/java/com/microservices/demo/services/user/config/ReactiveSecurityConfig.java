package com.microservices.demo.services.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class ReactiveSecurityConfig {



    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf()
                .disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET,"/users/current").permitAll()
                .pathMatchers(HttpMethod.GET,"/users/allUsers").hasAnyRole("USER", "ADMIN")
                .pathMatchers(HttpMethod.GET,"/users/findByUserId*", "/users/findByUserId/*",
                        "/users/findByUserId/**").hasRole("ADMIN")
                .pathMatchers("/**").authenticated()
                .and()
                .httpBasic()
                .and()
                .build();
    }
}


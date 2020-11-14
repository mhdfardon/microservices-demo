package com.microservices.demo.services.user.config;

import com.microservices.demo.services.dao.UserRepository;
import com.microservices.demo.services.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class ReactiveSecurityConfig {

    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf()
                .disable()
                .authorizeExchange()
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


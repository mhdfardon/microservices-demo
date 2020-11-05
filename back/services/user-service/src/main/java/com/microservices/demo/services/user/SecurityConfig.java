package com.microservices.demo.services.user;

import com.microservices.demo.services.user.dao.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.authorization.AuthorizationContext;

//@Configuration
public class SecurityConfig {

//    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    public static Mono<AuthorizationDecision> currentUserMatchesPath(Mono<Authentication> authentication, AuthorizationContext context) {
//        return authentication
//                .map(a -> context.getVariables().get("user").equals(a.getName()))
//                .map(AuthorizationDecision::new);
//    }

//    @Bean
//    public ReactiveUserDetailsService userDetailsService(UserRepository users) {
//        return users.findByUsername("username");
//
////                .map(u -> User.withUsername(u.getUsername())
////                        .password(u.getPassword())
////                        .authorities(u.getRoles().toArray(new String[0]))
////                        .accountExpired(!u.isActive())
////                        .credentialsExpired(!u.isActive())
////                        .disabled(!u.isActive())
////                        .accountLocked(!u.isActive())
////                        .build()
////                );
//    }
}

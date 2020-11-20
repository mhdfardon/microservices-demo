package com.microservices.demo.services.config;

import com.microservices.demo.services.dao.UserRepository;
import com.microservices.demo.services.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CommonSecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(UserRepository userRepository) {
        return (username) -> userRepository.findByUsername(username)
                .map(u -> User.withUsername(u.getUsername())
                        .password(encoder().encode(userService.decrypt(u.getPassword())))
                        .authorities(new SimpleGrantedAuthority(u.getRole()))
                        .accountExpired(false)
                        .credentialsExpired(false)
                        .disabled(false)
                        .accountLocked(false)
                        .build()

                );
    }
}

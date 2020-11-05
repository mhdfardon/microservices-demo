package com.microservices.demo.services.user;

import com.microservices.demo.model.user.User;
import com.microservices.demo.services.user.dao.UserRepository;
import com.microservices.demo.services.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
class DatabaseLoader {

    @Autowired
    private UserService userService;

    @Bean
    CommandLineRunner init(UserRepository repository) {

        return args -> {
            repository.save(new User("john", userService.encrypt("pass"), "john@gmail.com", "ROLE_USER"));
            repository.save(new User("sophie", userService.encrypt("pass"), "sohpie@gmail.com", "ROLE_ADMIN"));
            repository.save(new User("julien", userService.encrypt("pass"), "julien@gmail.com", "ROLE_USER"));
        };
    }

}

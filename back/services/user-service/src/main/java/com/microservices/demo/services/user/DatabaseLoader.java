package com.microservices.demo.services.user;

import com.microservices.demo.model.user.User;
import com.microservices.demo.services.user.dao.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
class DatabaseLoader {

    @Bean
    CommandLineRunner init(UserRepository repository) {

        return args -> {
            repository.save(new User("John", "pass1", "john@gmail.com", "ROLE_USER"));
            repository.save(new User("Sophie", "sohpie@gmail.com", "pass02", "ROLE_ADMIN"));
            repository.save(new User("Julien", "pass03", "julien@gmail.com", "ROLE_USER"));
        };
    }

}

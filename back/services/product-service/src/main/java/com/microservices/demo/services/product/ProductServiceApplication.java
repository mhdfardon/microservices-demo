package com.microservices.demo.services.product;

import com.microservices.demo.model.Product;
import com.microservices.demo.model.User;
import com.microservices.demo.services.dao.UserRepository;
import com.microservices.demo.services.product.dao.ProductRepository;
import com.microservices.demo.services.service.UserService;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator;

import java.time.Duration;
import java.util.Arrays;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication//(exclude = { SecurityAutoConfiguration.class })
@ComponentScan(basePackages = {"com.microservices.demo.model", "com.microservices.demo.services"})
@EntityScan({"com.microservices.demo.model", "com.microservices.demo.services"})
//@EnableJpaRepositories({"com.microservices.demo.model", "com.microservices.demo.services"})
public class ProductServiceApplication {//} extends SpringBootServletInitializer {
    public static void main(String[] args) {

        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

        return initializer;
    }

    @Bean
    public CommandLineRunner demo(ProductRepository productRepository, UserRepository userRepository, UserService userService) {

        return (args) -> {

            userRepository.saveAll(Arrays.asList(
                    new User("john", userService.encrypt("pass"), "john@gmail.com", "ROLE_USER"),
                    new User("sophie", userService.encrypt("pass"), "sohpie@gmail.com", "ROLE_ADMIN"),
                    new User("julien", userService.encrypt("pass"), "julien@gmail.com", "ROLE_USER")
            )).blockLast(Duration.ofSeconds(5));

            productRepository.saveAll(Arrays.asList(
                    new Product("S7", "Smartphone Samsung Galaxy S7", 700.00),
                    new Product("IPhone 10", "Smartphone IPhone 10", 900.00)
            )).blockLast(Duration.ofSeconds(5));
        };
    }
}

package com.microservices.demo.services.user;

import com.microservices.demo.model.User;
import com.microservices.demo.services.dao.UserRepository;
import com.microservices.demo.services.service.UserService;
import io.r2dbc.spi.ConnectionFactory;
import org.h2.tools.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.sql.SQLException;
import java.time.Duration;
import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = {"com.microservices.demo.model", "com.microservices.demo.services"})
@EntityScan({"com.microservices.demo.model", "com.microservices.demo.services"})
@EnableR2dbcRepositories(basePackages = "com.microservices.demo.services.dao")
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

        return initializer;
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository, UserService userService) {
        return (args) -> userRepository.saveAll(Arrays.asList(
                new User("john", userService.encrypt("pass"), "john@gmail.com", "ROLE_USER"),
                new User("sophie", userService.encrypt("pass"), "sohpie@gmail.com", "ROLE_ADMIN"),
                new User("julien", userService.encrypt("pass"), "julien@gmail.com", "ROLE_USER")
        )).blockLast(Duration.ofSeconds(5));
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server inMemoryH2DatabaseServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "8090");
    }
}

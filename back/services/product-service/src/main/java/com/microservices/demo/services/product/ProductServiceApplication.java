package com.microservices.demo.services.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.microservices.demo.model.product", "com.microservices.demo.services"})
@EntityScan({"com.microservices.demo.model.product", "com.microservices.demo.services"})
@EnableJpaRepositories({"com.microservices.demo.model.product", "com.microservices.demo.services"})
public class ProductServiceApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}

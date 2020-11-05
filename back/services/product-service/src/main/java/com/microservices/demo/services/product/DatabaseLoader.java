package com.microservices.demo.services.product;

import com.microservices.demo.model.product.Product;
import com.microservices.demo.model.user.User;
import com.microservices.demo.services.product.dao.ProductRepository;
import com.microservices.demo.services.product.dao.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
class DatabaseLoader {

    @Bean
    CommandLineRunner init(ProductRepository productRepository) {

        return args -> {
            productRepository.save(new Product("S7", "Smartphone Samsung Galaxy S7", 700.00));
            productRepository.save(new Product("IPhone 10", "Smartphone IPhone 10", 900.00));
        };
    }

}

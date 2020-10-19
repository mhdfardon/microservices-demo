package com.microservices.demo.services.product;

import com.microservices.demo.model.product.Product;
import com.microservices.demo.model.user.User;
import com.microservices.demo.services.product.dao.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
class DatabaseLoader {

    @Bean
    CommandLineRunner init(ProductRepository repository) {

        return args -> {
            repository.save(new Product("S7", "Smartphone Samsung Galaxy S7", 700.00));
            repository.save(new Product("IPhone 10", "Smartphone IPhone 10", 900.00));
        };
    }

}

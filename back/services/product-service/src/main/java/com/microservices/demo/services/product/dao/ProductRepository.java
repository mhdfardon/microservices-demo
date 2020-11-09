package com.microservices.demo.services.product.dao;

import com.microservices.demo.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}

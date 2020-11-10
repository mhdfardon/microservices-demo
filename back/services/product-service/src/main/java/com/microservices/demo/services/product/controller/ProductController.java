package com.microservices.demo.services.product.controller;

import com.microservices.demo.model.Product;
import com.microservices.demo.services.product.dao.ProductRepository;
import com.microservices.demo.services.user.dao.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductRepository productRespository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/findByProductId")
    public Product findByProductId(@RequestParam() long id) {
        return productRespository.findById(id).get();
    }

    @GetMapping("/allProducts")
    public List<Product> findAllProducts() {

        //List<User> users = userRepository.findAll();
        List<Product> products = new ArrayList<>();
        productRespository.findAll().forEach(products::add);
        log.info("Found " + products.size() + " products");
        return products;
    }

    @GetMapping(value = "/create")
    public String create(@RequestParam String name, @RequestParam String description, @RequestParam Double price) {
        Product product = new Product(name, description, price);
        productRespository.save(product);

        return "OK";
    }

    @PutMapping("/createProduct")
    public void createProduct(@RequestBody Product product) {
        productRespository.save(product);
    }

    @DeleteMapping("deleteById")
    public void deleteById(@RequestParam long id) {

        productRespository.deleteById(id);
    }
}

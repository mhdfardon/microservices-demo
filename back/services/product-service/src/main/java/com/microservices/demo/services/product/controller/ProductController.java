package com.microservices.demo.services.product.controller;

import com.microservices.demo.model.Product;
import com.microservices.demo.model.User;
import com.microservices.demo.services.product.dao.ProductRepository;
import com.microservices.demo.services.user.dao.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<ResponseEntity<Product>> findByProductId(@RequestParam() long id) {
        Product product = productRespository.findById(id).get();
        if (product != null) {
            return Mono.just(new ResponseEntity<>(product, HttpStatus.OK));
        }
        return Mono.just(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/allProducts")
    public Mono<ResponseEntity<Flux<Product>>> findAllProducts() {

        List<Product> products = productRespository.findAll();
        log.info("Found " + products.size() + " products");
        return Flux.fromIterable(products)
                .collectList()
                .map(body -> new ResponseEntity<>(Flux.fromIterable(body), HttpStatus.OK));
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

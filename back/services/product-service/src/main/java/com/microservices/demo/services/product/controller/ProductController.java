package com.microservices.demo.services.product.controller;

import com.microservices.demo.model.Product;
import com.microservices.demo.services.dao.UserRepository;
import com.microservices.demo.services.product.dao.ProductRepository;
import com.microservices.demo.services.product.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductRepository productRespository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/findByProductId")
    public Mono<ResponseEntity<Mono<Product>>> findByProductId(@RequestParam() long id) {
        Mono<Product> product = productRespository.findById(id);

        return Mono.just(new ResponseEntity<>(product, HttpStatus.OK))
                .switchIfEmpty(Mono.error(new ProductNotFoundException()));
    }


    @GetMapping("/allProducts")
    public Mono<ResponseEntity<Flux<Product>>> findAllProducts() {
        return Mono.just(new ResponseEntity<>(productRespository.findAll(), HttpStatus.OK))
                .switchIfEmpty(Mono.error(new ProductNotFoundException()));
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(@SuppressWarnings("unused") ProductNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}

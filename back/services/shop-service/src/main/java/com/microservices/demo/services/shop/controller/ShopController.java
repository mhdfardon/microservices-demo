package com.microservices.demo.services.shop.controller;

import com.microservices.demo.model.Product;
import com.microservices.demo.model.User;
import com.microservices.demo.services.shop.exception.ProductNotFoundException;
import com.microservices.demo.services.shop.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/shop")
@Slf4j
public class ShopController {

    @Value("${user.service.uri}")
    private String userServiceUri;
    @Value("${user.service.params.findByUserId}")
    private String userServiceFindByUserIdParams;

    @Value("${product.service.uri}")
    private String productServiceUri;
    @Value("${product.service.params.create}")
    private String productServiceCreateParams;

    @Autowired
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping("/findByProductId")
    public Mono<ResponseEntity<Product>> findByProductId(@RequestParam() String id) {
        return restTemplate().getForObject("http://localhost:8081/products/findByProductId", Mono.class, id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException()));
    }

    @GetMapping("/allUsers")
    public Mono<ResponseEntity<List<User>>> findAllUsers() {
        String url = "http://localhost:8082/users/allUsers";
        HttpEntity request = createRequest();
        ResponseEntity<List<User>> users = new RestTemplate().exchange(url, HttpMethod.GET,
                request, new ParameterizedTypeReference<List<User>>() {});
        log.info("Found " + users.getBody().size() + " users");
        return Mono.just(users).switchIfEmpty(Mono.error(new UserNotFoundException()));
    }

    @GetMapping("/createProduct")
    public String create(@RequestParam String userid, @RequestParam String name, @RequestParam String description, @RequestParam Double price) {

        try {

            User user = restTemplate().getForObject(userServiceUri + userServiceFindByUserIdParams, User.class, userid);
            if(user != null) {
                restTemplate().getForObject(productServiceUri +
                        productServiceCreateParams, String.class, name, description, price);
                return "Product successfully created";
            } else {
                return "User does not exist";
            }
        }catch (Exception e) {
            return "ERROR : " + e.getMessage();
        }
    }

    private HttpEntity createRequest() {
        String authStr = "sophie:pass";
        String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        HttpEntity request = new HttpEntity(headers);
        return request;
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(@SuppressWarnings("unused") ProductNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(@SuppressWarnings("unused") UserNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

}

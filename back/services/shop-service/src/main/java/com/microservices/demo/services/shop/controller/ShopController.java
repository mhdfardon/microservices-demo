package com.microservices.demo.services.shop.controller;

import com.microservices.demo.model.product.Product;
import com.microservices.demo.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/shop")
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
    public Product findByProductId(@RequestParam() String id) {
        Product product = restTemplate().getForObject("http://localhost:8081/products/findByProductId", Product.class, id);
        return product;
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

}

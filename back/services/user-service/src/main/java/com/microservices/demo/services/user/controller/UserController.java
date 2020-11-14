package com.microservices.demo.services.user.controller;

import com.microservices.demo.model.User;
import com.microservices.demo.services.dao.UserRepository;
import com.microservices.demo.services.service.UserService;
import com.microservices.demo.services.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/allUsers")
    public Mono<ResponseEntity<Flux<User>>> findAllUsers() {
        return Mono.just(new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK))
                .switchIfEmpty(Mono.error(new UserNotFoundException()));
    }

    @GetMapping("/findByUserId")
    public Mono<ResponseEntity<Mono<User>>> findByUserId(@RequestParam long id) {
        Mono<User> user = userRepository.findById(id);
        user = userService.decryptUser(user);
        return Mono.just(new ResponseEntity<>(user, HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(null, HttpStatus.NOT_FOUND)));
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(@SuppressWarnings("unused") UserNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}

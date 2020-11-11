package com.microservices.demo.services.user.controller;

import com.microservices.demo.model.User;
import com.microservices.demo.services.user.dao.UserRepository;
import com.microservices.demo.services.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/allUsers")
    public Flux<User> findAllUsers() {
        final List<User> users = userRepository.findAll();
        users.forEach(user -> user.setPassword(userService.decrypt(user.getPassword())));
        log.info("Found " + users.size() + " users");
        return Flux.fromIterable(users);
    }

    @GetMapping("/allUsers2")
    public Mono<ResponseEntity<Flux<User>>> findAllUsers2() {
        final List<User> users = userRepository.findAll();
        users.forEach(user -> user.setPassword(userService.decrypt(user.getPassword())));
        log.info("Found " + users.size() + " users");
        return Flux.fromIterable(users)
                .collectList()
                .map(body -> new ResponseEntity<>(Flux.fromIterable(body), HttpStatus.OK));
    }

    @GetMapping("/allUsers3")
    public Mono<ResponseEntity<List<User>>> findAllUsers3() {
        final List<User> users = userRepository.findAll();
        users.forEach(user -> user.setPassword(userService.decrypt(user.getPassword())));
        log.info("Found " + users.size() + " users");
        return Flux.fromIterable(users)
                .collectList()
                .map(body -> new ResponseEntity<>(body, HttpStatus.OK));
    }

    @GetMapping("/findByUserId")
    public Mono<User> findByUserId(@RequestParam long id) {
        User user = userRepository.findById(id).get();
        user.setPassword(userService.decrypt(user.getPassword()));
        return Mono.just(user);
    }

    @GetMapping("/user/{username}")
    public Mono<ResponseEntity<User>> getUserByName(@PathVariable("username") String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setPassword(userService.decrypt(user.getPassword()));
        }

        return Mono.just(new ResponseEntity<>(user, HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(null, HttpStatus.NOT_FOUND)));
    }


    @PutMapping("/admin/create")
    public void create(@RequestBody final User user) {
        userRepository.save(user);
    }

    @DeleteMapping("/admin/deleteById")
    public void deleteById(final @RequestParam long id) {
        userRepository.delete(userRepository.findById(id).get());
    }
}

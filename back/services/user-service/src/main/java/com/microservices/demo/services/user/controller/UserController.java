package com.microservices.demo.services.user.controller;

import com.microservices.demo.model.User;
import com.microservices.demo.services.dao.UserRepository;
import com.microservices.demo.services.service.UserService;
import com.microservices.demo.services.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @GetMapping("/userdetails")
    public String principal(@AuthenticationPrincipal UserDetails userDetails) {
        return "Hello " + userDetails.getUsername();
    }


    @GetMapping("/current")
    public Mono<Map> current(@AuthenticationPrincipal Mono<Principal> principal) {
        return principal
                .map(user -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", user.getName());
                    map.put("roles", AuthorityUtils.authorityListToSet(((Authentication) user)
                            .getAuthorities()));
                    return map;
                });
    }


    public Mono<ServerResponse> hello(ServerRequest serverRequest) {
        return serverRequest.principal()
                .map(Principal::getName)
                .flatMap(username ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .syncBody(Collections.singletonMap("message", "Hello " + username + "!"))
                );
    }

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

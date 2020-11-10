package com.microservices.demo.services.user.controller;

import com.microservices.demo.model.dto.UserDTO;
import com.microservices.demo.model.User;
import com.microservices.demo.services.user.dao.UserRepository;
import com.microservices.demo.services.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public List<User> findAllUsers() {
        final List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        log.info("Found " + users.size() + " users");
        return users;
    }

    @GetMapping("/findByUserId")
    public UserDTO findByUserId(@RequestParam long id) {
        User user = userRepository.findById(id).get();
        return userService.convertUser(user);
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

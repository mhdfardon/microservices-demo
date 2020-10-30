package com.microservices.demo.services.user.controller;

import com.microservices.demo.model.dto.UserDTO;
import com.microservices.demo.model.user.User;
import com.microservices.demo.services.user.dao.UserRepository;
import com.microservices.demo.services.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRespository;

    @Autowired
    private UserService userService;

    @GetMapping("/findByUserId")
    public UserDTO findByUserId(@RequestParam long id) {
        User user = userRespository.findById(id).get();
        UserDTO userDTO = userService.convertUser(user);

        return userDTO;
    }

    @GetMapping("/allUsers")
    public List<User> findAllUsers() {
        final List<User> users = new ArrayList<>();
        userRespository.findAll().forEach(users::add);

        return users;

    }

    @PutMapping("/create")
    public void create(@RequestBody final User user) {
        userRespository.save(user);
    }

    @DeleteMapping("/deleteById")
    public void deleteById(final @RequestParam long id) {

        userRespository.delete(userRespository.findById(id).get());
    }
}

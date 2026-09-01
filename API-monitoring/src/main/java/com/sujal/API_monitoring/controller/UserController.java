package com.sujal.API_monitoring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sujal.API_monitoring.service.UserService;

import org.springframework.web.bind.annotation.RequestBody;

import com.sujal.API_monitoring.entity.User;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

 @PostMapping
public User createUser(@RequestBody User user) {

    System.out.println("Name: " + user.getName());
    System.out.println("Email: " + user.getEmail());
    System.out.println("Password: " + user.getPassword());
    System.out.println("Role: " + user.getRole());

    return userService.createUser(user);

    }
}

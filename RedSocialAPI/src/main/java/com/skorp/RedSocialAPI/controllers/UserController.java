package com.skorp.RedSocialAPI.controllers;

import com.skorp.RedSocialAPI.models.User;
import com.skorp.RedSocialAPI.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/register")
    public User saveUser(@RequestBody User user) {
        return userService.register(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.login(user);
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }
}

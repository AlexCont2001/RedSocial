package com.skorp.RedSocialAPI.user;

import com.skorp.RedSocialAPI.user.dto.UserCreateDTO;
import com.skorp.RedSocialAPI.user.dto.UserResponseDTO;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody UserCreateDTO userDTO) {
        return userService.register(userDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        return userService.login(user);
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }
}

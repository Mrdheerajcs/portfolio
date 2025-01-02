package com.auspicius.Controller;

import com.auspicius.Entity.User;
import com.auspicius.Services.UserService;
import com.auspicius.responce.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ApiResponse<User> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @PostMapping("/save")
    public ApiResponse<User> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public ApiResponse<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser(@PathVariable UUID id) {
        return userService.deleteUser(id);
    }
}

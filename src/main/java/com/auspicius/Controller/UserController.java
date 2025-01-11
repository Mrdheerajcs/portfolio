package com.auspicius.Controller;

import com.auspicius.Entity.User;
import com.auspicius.Services.UserService;
import com.auspicius.responce.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getBy/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Integer id) {
        ApiResponse<User> response = userService.getUserById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) {
        ApiResponse<User> response = userService.createUser(user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/updateBy/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Integer id, @Valid @RequestBody User user) {
        ApiResponse<User> response = userService.updateUser(id, user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/deleteBy/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Integer id) {
        ApiResponse<String> response = userService.deleteUser(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}

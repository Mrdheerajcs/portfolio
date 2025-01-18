package com.auspicius.Controller;

import com.auspicius.Entity.User;
import com.auspicius.Services.UserService;
import com.auspicius.exception.SDDException;
import com.auspicius.responce.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<User>>> getAllUser() {
        ApiResponse<List<User>> response = userService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) {
        ApiResponse<User> response = userService.createUser(user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<ApiResponse<User>> updateUserStatus(@PathVariable Integer id, @RequestParam Boolean status) {
        ApiResponse<User> response = userService.updateUserStatus(id, status);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @PutMapping("/update")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String email,
            @Valid @RequestBody User user
    ) {
        if (id == null && email == null) {
            throw new SDDException("id or email", HttpStatus.BAD_REQUEST.value(), "Either ID or Email must be provided");
        }

        ApiResponse<User> response = userService.updateUser(id, email, user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Integer id) {
        ApiResponse<String> response = userService.deleteUser(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}

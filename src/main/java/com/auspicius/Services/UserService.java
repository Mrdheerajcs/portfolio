package com.auspicius.Services;

import com.auspicius.Entity.User;
import com.auspicius.responce.ApiResponse;

import java.util.UUID;

public interface UserService {

    ApiResponse<User> getUserById(UUID id);

    ApiResponse<User> createUser(User user);

    ApiResponse<User> updateUser(UUID id, User user);

    ApiResponse<String> deleteUser(UUID id);
}

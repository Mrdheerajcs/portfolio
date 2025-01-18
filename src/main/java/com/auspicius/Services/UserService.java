package com.auspicius.Services;

import com.auspicius.Entity.User;
import com.auspicius.responce.ApiResponse;

import java.util.List;


public interface UserService {

    ApiResponse<User> getUserById(Integer id);

    ApiResponse<List<User>> getAllUser();

    ApiResponse<User> createUser(User user);

    ApiResponse<String> deleteUser(Integer id);

    ApiResponse<User> updateUser(Integer id, String email, User user);

    ApiResponse<User> updateUserStatus(Integer id, Boolean status);
}

package com.auspicius.Services;

import com.auspicius.Entity.User;
import com.auspicius.responce.ApiResponse;


public interface UserService {

    ApiResponse<User> getUserById(Integer id);

    ApiResponse<User> createUser(User user);

    ApiResponse<User> updateUser(Integer id, User user);

    ApiResponse<String> deleteUser(Integer id);
}

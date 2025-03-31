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

    ApiResponse<String> changePassword(String email, String oldPassword, String newPassword);

    ApiResponse<String> sendResetPasswordOTP(String email);
    ApiResponse<String> resetPassword(String email, String otp, String newPassword);

}

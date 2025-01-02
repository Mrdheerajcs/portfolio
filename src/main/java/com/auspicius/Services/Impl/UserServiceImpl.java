package com.auspicius.Services.Impl;

import com.auspicius.Entity.User;
import com.auspicius.Repository.UserRepository;
import com.auspicius.Services.UserService;
import com.auspicius.responce.ApiResponse;
import com.auspicius.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiResponse<User> getUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return ResponseUtils.createSuccessResponse(user.get());
        } else {
            return ResponseUtils.createFailureResponse(
                    "User not found",
                    HttpStatus.NOT_FOUND.value()
            );
        }
    }

    @Override
    public ApiResponse<User> createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseUtils.createFailureResponse(
                    "Email already in use",
                    HttpStatus.BAD_REQUEST.value()
            );
        }
        User savedUser = userRepository.save(user);
        return ResponseUtils.createSuccessResponse(savedUser);
    }

    @Override
    public ApiResponse<User> updateUser(UUID id, User user) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setEmail(user.getEmail());
            existingUser.setName(user.getName());
            existingUser.setTitle(user.getTitle());
            existingUser.setPhone(user.getPhone());
            existingUser.setAboutMe(user.getAboutMe());
            existingUser.setProfilePicture(user.getProfilePicture());
            existingUser.setStatus(user.getStatus());
            existingUser.setSocialLinks(user.getSocialLinks());

            User updatedUser = userRepository.save(existingUser);
            return ResponseUtils.createSuccessResponse(updatedUser);
        } else {
            return ResponseUtils.createFailureResponse(
                    "User not found",
                    HttpStatus.NOT_FOUND.value()
            );
        }
    }

    @Override
    public ApiResponse<String> deleteUser(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return ResponseUtils.createSuccessResponse("User deleted successfully");
        } else {
            return ResponseUtils.createFailureResponse(
                    "User not found",
                    HttpStatus.NOT_FOUND.value()
            );
        }
    }
}

package com.auspicius.Services.Impl;

import com.auspicius.Entity.User;
import com.auspicius.Repository.UserRepository;
import com.auspicius.Services.UserService;
import com.auspicius.exception.RecordNotFoundException;
import com.auspicius.exception.SDDException;
import com.auspicius.helperUtil.Helper;
import com.auspicius.responce.ApiResponse;
import com.auspicius.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiResponse<User> getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User with ID " + id + " not found"));
        return ResponseUtils.createSuccessResponse(user);
    }

    @Override
    public ApiResponse<User> createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new SDDException("email", HttpStatus.BAD_REQUEST.value(), "Email already in use");
        }

        // Validate and set default values
        validateUser(user);
        user.setCreatedOn(Helper.getCurrentTimeStamp());
        user.setUpdatedOn(Helper.getCurrentTimeStamp());
        user.setStatus(user.getStatus() != null ? user.getStatus() : true);

        User savedUser = userRepository.save(user);
        return ResponseUtils.createSuccessResponse(savedUser);
    }

    @Override
    public ApiResponse<User> updateUser(Integer id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User with ID " + id + " not found"));

        // Update fields
        validateUser(user);
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setTitle(user.getTitle());
        existingUser.setPhone(user.getPhone());
        existingUser.setAboutMe(user.getAboutMe());
        existingUser.setProfilePicture(user.getProfilePicture());
        existingUser.setStatus(user.getStatus());
        existingUser.setSocialLinks(user.getSocialLinks());
        existingUser.setUpdatedOn(Helper.getCurrentTimeStamp());

        User updatedUser = userRepository.save(existingUser);
        return ResponseUtils.createSuccessResponse(updatedUser);
    }

    @Override
    public ApiResponse<String> deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User with ID " + id + " not found"));

        userRepository.delete(user);
        return ResponseUtils.createSuccessResponse("User deleted successfully");
    }

    private void validateUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            throw new SDDException("name", HttpStatus.BAD_REQUEST.value(), "Name cannot be blank");
        }
        if (user.getEmail() == null || !user.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new SDDException("email", HttpStatus.BAD_REQUEST.value(), "Invalid email format");
        }
        if (user.getPhone() == null || user.getPhone().isBlank()) {
            throw new SDDException("phone", HttpStatus.BAD_REQUEST.value(), "Phone number cannot be blank");
        }
        if (user.getTitle() == null || user.getTitle().isBlank()) {
            throw new SDDException("title", HttpStatus.BAD_REQUEST.value(), "title cannot be blank");
        }
        if (user.getAboutMe() == null || user.getAboutMe().isBlank()) {
            throw new SDDException("aboutMe", HttpStatus.BAD_REQUEST.value(), "AboutMe cannot be blank");
        }

    }
}

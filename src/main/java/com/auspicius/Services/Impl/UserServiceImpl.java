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

import java.util.List;
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
    public ApiResponse<List<User>> getAllUser() {
        List<User> users = userRepository.findAll();
        return ResponseUtils.createSuccessResponse(users.isEmpty() ? List.of() : users);
    }

    @Override
    public ApiResponse<User> createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new SDDException("email", HttpStatus.BAD_REQUEST.value(), "Email already in use");
        }
        validateUser(user);
        user.setCreatedOn(Helper.getCurrentTimeStamp());
        user.setUpdatedOn(Helper.getCurrentTimeStamp());
        user.setStatus(Boolean.TRUE.equals(user.getStatus()));

        User savedUser = userRepository.save(user);
        return ResponseUtils.createSuccessResponse(savedUser);
    }

    @Override
    public ApiResponse<User> updateUser(Integer id, String email, User user) {
        Optional<User> existingUser;

        if (id != null) {
            existingUser = userRepository.findById(id);
        } else {
            existingUser = userRepository.findByEmail(email);
        }

        // Handle user not found
        if (existingUser.isEmpty()) {
            throw new SDDException("user", HttpStatus.NOT_FOUND.value(), "User not found");
        }

        User userToUpdate = existingUser.get();

        if (user.getName() != null) userToUpdate.setName(user.getName());
        if (user.getTitle() != null) userToUpdate.setTitle(user.getTitle());
        if (user.getPhone() != null) userToUpdate.setPhone(user.getPhone());
        if (user.getAboutMe() != null) userToUpdate.setAboutMe(user.getAboutMe());
        if (user.getProfilePicture() != null) userToUpdate.setProfilePicture(user.getProfilePicture());
        if (user.getStatus() != null) userToUpdate.setStatus(user.getStatus());
        if (user.getSocialLinks() != null) userToUpdate.setSocialLinks(user.getSocialLinks());

        userToUpdate.setUpdatedOn(Helper.getCurrentTimeStamp());
        userToUpdate.setStatus(userToUpdate.getStatus());

        User updatedUser = userRepository.save(userToUpdate);
        return ResponseUtils.createSuccessResponse(updatedUser);
}

    @Override
    public ApiResponse<User> updateUserStatus(Integer id, Boolean status) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User with ID " + id + " not found"));

        existingUser.setStatus(status);
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
    }

    private void copyUserDetails(User source, User target) {
        target.setName(source.getName());
        target.setEmail(source.getEmail());
        target.setTitle(source.getTitle());
        target.setPhone(source.getPhone());
        target.setAboutMe(source.getAboutMe());
        target.setProfilePicture(source.getProfilePicture());
        target.setStatus(source.getStatus());
        target.setSocialLinks(source.getSocialLinks());
        target.setUpdatedOn(Helper.getCurrentTimeStamp());
    }
}

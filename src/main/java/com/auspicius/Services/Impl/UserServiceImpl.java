package com.auspicius.Services.Impl;

import com.auspicius.Entity.User;
import com.auspicius.Repository.UserRepository;
import com.auspicius.Services.UserService;
import com.auspicius.exception.RecordNotFoundException;
import com.auspicius.exception.SDDException;
import com.auspicius.helperUtil.Helper;
import com.auspicius.responce.ApiResponse;
import com.auspicius.utils.ResponseUtils;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    private Map<String, String> otpStorage = new HashMap<>();

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

        if (existingUser.isEmpty()) {
            throw new SDDException("user", HttpStatus.NOT_FOUND.value(), "User not found");
        }

        User userToUpdate = existingUser.get();

        // Check if email is being updated
        if (user.getEmail() != null && !user.getEmail().equals(userToUpdate.getEmail())) {
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new SDDException("email", HttpStatus.BAD_REQUEST.value(), "Email already in use");
            }
            userToUpdate.setEmail(user.getEmail());
        }

        if (user.getName() != null) userToUpdate.setName(user.getName());
        if (user.getTitle() != null) userToUpdate.setTitle(user.getTitle());
        if (user.getPhone() != null) userToUpdate.setPhone(user.getPhone());
        if (user.getAboutMe() != null) userToUpdate.setAboutMe(user.getAboutMe());
        if (user.getProfilePicture() != null) userToUpdate.setProfilePicture(user.getProfilePicture());
        if (user.getStatus() != null) userToUpdate.setStatus(user.getStatus());

        userToUpdate.setUpdatedOn(Helper.getCurrentTimeStamp());
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

    @Override
    public ApiResponse<String> changePassword(String email, String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new SDDException("user", HttpStatus.NOT_FOUND.value(), "User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new SDDException("password", HttpStatus.BAD_REQUEST.value(), "Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedOn(Helper.getCurrentTimeStamp());
        userRepository.save(user);

        return ResponseUtils.createSuccessResponse("Password changed successfully");
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
        target.setUpdatedOn(Helper.getCurrentTimeStamp());
    }

    @Override
    public ApiResponse<String> sendResetPasswordOTP(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new SDDException("user", HttpStatus.NOT_FOUND.value(), "User not found"));

        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStorage.put(email, otp);

        // Send email
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Password Reset OTP");
            helper.setText("Your OTP for password reset is: " + otp);
            mailSender.send(message);
        } catch (Exception e) {
            throw new SDDException("email", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to send email");
        }

        return ResponseUtils.createSuccessResponse("OTP sent successfully to " + email);
    }

    @Override
    public ApiResponse<String> resetPassword(String email, String otp, String newPassword) {
        if (!otpStorage.containsKey(email) || !otpStorage.get(email).equals(otp)) {
            throw new SDDException("otp", HttpStatus.BAD_REQUEST.value(), "Invalid OTP");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new SDDException("user", HttpStatus.NOT_FOUND.value(), "User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedOn(Helper.getCurrentTimeStamp());
        userRepository.save(user);

        otpStorage.remove(email);

        return ResponseUtils.createSuccessResponse("Password reset successfully");
    }
}

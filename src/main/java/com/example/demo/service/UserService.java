package com.example.demo.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Role;
import com.example.demo
.entity.User;
import com.example.demo.exception.*;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // ================= CREATE USER =================
    public User createUser(User user) {

        if (userRepo.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        // 🔐 Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Default role
        user.setRole(Role.USER);
        user.setProvider("LOCAL");

        return userRepo.save(user);
    }

    // ================= GET USER BY ID =================
    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // ================= GET ALL USERS (ADMIN ONLY) =================
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // ================= UPDATE USER =================
    public User updateUser(Long id, User updatedUser) {

        User user = getUserById(id);

        // Only update allowed fields
        user.setName(updatedUser.getName());
        user.setCity(updatedUser.getCity());
        user.setState(updatedUser.getState());
        user.setCountry(updatedUser.getCountry());

        // ❌ Do NOT allow email & role update here
        // user.setEmail(updatedUser.getEmail());
        // user.setRole(updatedUser.getRole());

        return userRepo.save(user);
    }

    // ================= DELETE USER =================
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepo.delete(user);
    }

    // ================= GET CURRENT USER =================
    public User getCurrentUser() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}

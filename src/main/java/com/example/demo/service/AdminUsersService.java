package com.example.demo.service;

import java.util.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.UserDetailsDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;

@Service
@Transactional
public class AdminUsersService {

    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;
    private final AiInsightRepository insightRepository;

    public AdminUsersService(UserRepository userRepository,
                              ExpenseRepository expenseRepository,
                              BudgetRepository budgetRepository,
                              AiInsightRepository insightRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
        this.budgetRepository = budgetRepository;
        this.insightRepository = insightRepository;
    }

    /**
     * Get all users with pagination, search, and status filter
     */
    public Page<UserDetailsDTO> getAllUsers(Pageable pageable, String search, String status) {
        Page<User> users;

        boolean hasSearch = search != null && !search.trim().isEmpty();
        boolean hasStatus = status != null && !status.trim().isEmpty();

        if (hasSearch && hasStatus) {
            // ✅ UPDATED: renamed method in UserRepository_CLEAN.java
            users = userRepository.searchByNameOrEmailAndStatus(search.trim(), status, pageable);
        } else if (hasSearch) {
            users = userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    search.trim(), search.trim(), pageable);
        } else if (hasStatus) {
            users = userRepository.findByStatus(status, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }

        return users.map(this::convertToDTO);
    }

    /**
     * Get user details by ID with full statistics
     */
    public UserDetailsDTO getUserDetails(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        UserDetailsDTO dto = convertToDTO(user);

        Long expenseCount = expenseRepository.countByUser_UserId(userId);
        Long budgetCount  = budgetRepository.countByUser_UserId(userId);
        Long insightCount = insightRepository.countByUser_UserId(userId);

        dto.setExpenseCount(expenseCount != null ? expenseCount.intValue() : 0);

        return dto;
    }

    /**
     * Block a user
     */
    public Map<String, Object> blockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if ("BLOCKED".equals(user.getStatus())) {
            throw new IllegalStateException("User is already blocked");
        }

        user.setStatus("BLOCKED");
        userRepository.save(user);

        return Map.of(
                "success", true,
                "message", "User blocked successfully",
                "userId",  userId,
                "status",  "BLOCKED"
        );
    }

    /**
     * Unblock a user
     */
    public Map<String, Object> unblockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if ("ACTIVE".equals(user.getStatus())) {
            throw new IllegalStateException("User is already active");
        }

        user.setStatus("ACTIVE");
        userRepository.save(user);

        return Map.of(
                "success", true,
                "message", "User unblocked successfully",
                "userId",  userId,
                "status",  "ACTIVE"
        );
    }

    /**
     * Delete a user permanently
     * Cascades to delete all related data (expenses, budgets, insights, etc.)
     */
    public Map<String, Object> deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        userRepository.delete(user);

        return Map.of(
                "success", true,
                "message", "User deleted successfully",
                "userId",  userId
        );
    }

    /**
     * Convert User entity to DTO with calculated fields
     */
    private UserDetailsDTO convertToDTO(User user) {
        UserDetailsDTO dto = new UserDetailsDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCity(user.getCity());
        dto.setState(user.getState());
        dto.setCountry(user.getCountry());
        dto.setStatus(user.getStatus() != null ? user.getStatus() : "ACTIVE");

        Double totalExpenses = expenseRepository.getTotalExpensesByUserId(user.getUserId());
        dto.setTotalExpenses(totalExpenses != null ? totalExpenses : 0.0);

        return dto;
    }
}
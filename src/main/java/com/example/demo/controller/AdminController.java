package com.example.demo.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.UserDetailsDTO;
import com.example.demo.service.AdminUsersService;

@RestController
@RequestMapping("/api/admin/users")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:5173",
    "https://ai-powered-finance-tracker.netlify.app"
}) // ✅ CORS should be before PreAuthorize
public class AdminController {

    private final AdminUsersService usersService;

    public AdminController(AdminUsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * Get all users with pagination, search, and filter
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")  // ✅ Method level
    public ResponseEntity<Page<UserDetailsDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status) {

        Page<UserDetailsDTO> users = usersService.getAllUsers(
            PageRequest.of(page, size), search, status);

        return ResponseEntity.ok(users);
    }

    /**
     * Get user details by ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")  // ✅ Method level
    public ResponseEntity<UserDetailsDTO> getUserDetails(@PathVariable Long id) {
        UserDetailsDTO user = usersService.getUserDetails(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Block a user
     */
    @PutMapping("/{id}/block")
    @PreAuthorize("hasRole('ADMIN')")  // ✅ Method level
    public ResponseEntity<Map<String, Object>> blockUser(@PathVariable Long id) {
        Map<String, Object> response = usersService.blockUser(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Unblock a user
     */
    @PutMapping("/{id}/unblock")
    @PreAuthorize("hasRole('ADMIN')")  // ✅ Method level
    public ResponseEntity<Map<String, Object>> unblockUser(@PathVariable Long id) {
        Map<String, Object> response = usersService.unblockUser(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a user permanently
     * ⚠️ This will delete all user data (expenses, incomes, etc.)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")  // ✅ Method level
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        Map<String, Object> response = usersService.deleteUser(id);
        return ResponseEntity.ok(response);
    }
}

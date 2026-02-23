package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.AdminStatsService;

@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:5173",
    "https://ai-powered-finance-tracker.netlify.app"
})
@RestController
@RequestMapping("/api/admin/stats")
@PreAuthorize("hasRole('ADMIN')")
public class AdminStatsController {

    private final AdminStatsService statsService;

    public AdminStatsController(AdminStatsService statsService) {
        this.statsService = statsService;
    }

    // ✅ GET /api/admin/stats - Dashboard cards data
    @GetMapping
    public ResponseEntity<?> getDashboardStats() {
        return ResponseEntity.ok(statsService.getDashboardStats());
    }

    // ✅ GET /api/admin/stats/expenses/monthly - Monthly expense chart
    @GetMapping("/expenses/monthly")
    public ResponseEntity<?> getMonthlyExpenses() {
        return ResponseEntity.ok(statsService.getMonthlyExpenses());
    }

    // ✅ GET /api/admin/stats/expenses/categories - Category pie chart
    @GetMapping("/expenses/categories")
    public ResponseEntity<?> getCategoryDistribution() {
        return ResponseEntity.ok(statsService.getCategoryDistribution());
    }
}

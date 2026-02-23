package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.AdminRevenueService;

@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:5173",
    "https://ai-powered-finance-tracker.netlify.app"
})
@RestController
@RequestMapping("/api/admin/revenue")
@PreAuthorize("hasRole('ADMIN')")
public class AdminRevenueController {

    private final AdminRevenueService revenueService;

    public AdminRevenueController(AdminRevenueService revenueService) {
        this.revenueService = revenueService;
    }

    /**
     * GET /api/admin/revenue/stats
     * Returns revenue overview statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<?> getRevenueStats() {
        return ResponseEntity.ok(revenueService.getRevenueStats());
    }

    /**
     * GET /api/admin/revenue/growth?months=6
     * Returns revenue growth data for last N months
     */
    @GetMapping("/growth")
    public ResponseEntity<?> getRevenueGrowth(
            @RequestParam(defaultValue = "6") int months) {
        return ResponseEntity.ok(revenueService.getRevenueGrowth(months));
    }

    /**
     * GET /api/admin/revenue/plans
     * Returns subscription plans breakdown
     */
    @GetMapping("/plans")
    public ResponseEntity<?> getSubscriptionPlans() {
        return ResponseEntity.ok(revenueService.getSubscriptionPlans());
    }
}

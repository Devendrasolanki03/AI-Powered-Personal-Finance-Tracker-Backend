package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.AdminAnalyticsService;

@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:5173",
    "https://ai-powered-finance-tracker.netlify.app"
})
@RestController
@RequestMapping("/api/admin/analytics")
@PreAuthorize("hasRole('ADMIN')")
public class AdminAnalyticsController {

    private final AdminAnalyticsService analyticsService;

    public AdminAnalyticsController(AdminAnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    /**
     * GET /api/admin/analytics/kpi
     * Returns key performance indicators
     * 
     * Response includes:
     * - avgExpensePerUser: Average expense across all users
     * - highestCategory: Category with most spending
     * - monthlyGrowth: Growth percentage vs previous month
     * - budgetUtilization: Average budget usage percentage
     */
    @GetMapping("/kpi")
    public ResponseEntity<?> getKPIMetrics() {
        return ResponseEntity.ok(analyticsService.getKPIMetrics());
    }

    /**
     * GET /api/admin/analytics/categories
     * Returns category-wise expense breakdown
     * 
     * Response includes for each category:
     * - category: Category name
     * - amount: Total amount spent
     * - percentage: Percentage of total expenses
     */
    @GetMapping("/categories")
    public ResponseEntity<?> getCategoryAnalytics() {
        return ResponseEntity.ok(analyticsService.getCategoryAnalytics());
    }

    /**
     * GET /api/admin/analytics/trends?year=2026
     * Returns monthly expense trends for specified year
     * 
     * Query Parameters:
     * - year: Year for trends (default: current year)
     */
    @GetMapping("/trends")
    public ResponseEntity<?> getExpenseTrends(
            @RequestParam(defaultValue = "2026") int year) {
        return ResponseEntity.ok(analyticsService.getExpenseTrends(year));
    }

    /**
     * GET /api/admin/analytics/distribution
     * Returns spending distribution by category type
     * 
     * Response includes:
     * - Essential: Food, Utilities, Healthcare (42%)
     * - Lifestyle: Shopping, Entertainment (28%)
     * - Savings: (22%)
     * - Investments: (8%)
     */
    @GetMapping("/distribution")
    public ResponseEntity<?> getSpendingDistribution() {
        return ResponseEntity.ok(analyticsService.getSpendingDistribution());
    }
}

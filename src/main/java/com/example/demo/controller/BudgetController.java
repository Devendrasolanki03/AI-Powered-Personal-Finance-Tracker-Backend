package com.example.demo.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.BudgetDTO;
import com.example.demo.entity.Budget;
import com.example.demo.service.BudgetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:5173",
    "https://ai-powered-finance-tracker.netlify.app"
})
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    // CREATE
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public Budget addBudget(@RequestBody @Valid BudgetDTO dto,
                            @AuthenticationPrincipal String email) {
        return budgetService.addBudget(email, dto);
    }

    // READ OWN BUDGETS
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<Budget> getMyBudgets(@AuthenticationPrincipal String email) {
        return budgetService.getBudgetsByUser(email);
    }

    // UPDATE
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{budgetId}")
    public Budget updateBudget(@PathVariable Long budgetId,
                               @RequestBody @Valid BudgetDTO dto,
                               @AuthenticationPrincipal String email) {
        return budgetService.updateBudget(budgetId, email, dto);
    }

    // DELETE
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{budgetId}")
    public String deleteBudget(@PathVariable Long budgetId,
                               @AuthenticationPrincipal String email) {
        budgetService.deleteBudget(budgetId, email);
        return "Budget deleted successfully";
    }

    // ALERTS - Returns empty list (frontend won't get 404 anymore)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/alerts")
    public List<Map<String, Object>> getBudgetAlerts(@AuthenticationPrincipal String email) {
        // Returns empty list - no 404 error
        // Implement full alert logic here later if needed
        return new ArrayList<>();
    }
}

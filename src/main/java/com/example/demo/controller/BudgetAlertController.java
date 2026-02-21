package com.example.demo.controller;

import java.util.List;
import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.BudgetAlertDTO;
import com.example.demo.service.BudgetAlertService;

@RestController
@RequestMapping("/api/budgets/alerts")  // ✅ FIXED: changed from /budget-alerts to /budgets/alerts
@CrossOrigin(origins = "http://localhost:3000")
public class BudgetAlertController {

    private final BudgetAlertService budgetAlertService;

    public BudgetAlertController(BudgetAlertService budgetAlertService) {
        this.budgetAlertService = budgetAlertService;
    }

    // GET ALL budget statuses (SAFE + WARNING + CRITICAL + EXCEEDED)
    // Endpoint: GET /api/budgets/alerts
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<BudgetAlertDTO> getAllAlerts(Principal principal) {
        return budgetAlertService.getBudgetAlerts(principal.getName());
    }

    // GET only active alerts (WARNING + CRITICAL + EXCEEDED)
    // Endpoint: GET /api/budgets/alerts/active
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/active")
    public List<BudgetAlertDTO> getActiveAlerts(Principal principal) {
        return budgetAlertService.getActiveAlerts(principal.getName());
    }
}
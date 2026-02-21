package com.example.demo.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.FinanceDashboardService;

@RestController
@RequestMapping("/api/dashboard")
@PreAuthorize("hasRole('USER')")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {

    private final FinanceDashboardService dashboardService;

    public DashboardController(FinanceDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public Map<String, Object> getDashboard(Principal principal, @RequestParam int year) {
        return dashboardService.getDashboard(principal.getName(), year);
    }
}

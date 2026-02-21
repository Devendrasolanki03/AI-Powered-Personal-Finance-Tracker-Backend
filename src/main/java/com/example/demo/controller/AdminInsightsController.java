package com.example.demo.controller;



import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.AdminInsightsService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/admin/insights")
@PreAuthorize("hasRole('ADMIN')")
public class AdminInsightsController {

    private final AdminInsightsService insightsService;

    public AdminInsightsController(AdminInsightsService insightsService) {
        this.insightsService = insightsService;
    }

    @GetMapping
    public ResponseEntity<?> getAllInsights(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String impact) {
        
        return ResponseEntity.ok(
            insightsService.getAllInsights(
                PageRequest.of(page, size), type, impact));
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getInsightsStats() {
        return ResponseEntity.ok(insightsService.getInsightsStats());
    }
}
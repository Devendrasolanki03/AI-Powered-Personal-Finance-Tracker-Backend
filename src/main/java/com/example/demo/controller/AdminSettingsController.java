package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.AdminSettingsService;

@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:5173",
    "https://ai-powered-finance-tracker.netlify.app"
})
@RestController
@RequestMapping("/api/admin/settings")
@PreAuthorize("hasRole('ADMIN')")
public class AdminSettingsController {

    private final AdminSettingsService settingsService;

    public AdminSettingsController(AdminSettingsService settingsService) {
        this.settingsService = settingsService;
    }

    /**
     * GET /api/admin/settings
     * Returns all system settings
     */
    @GetMapping
    public ResponseEntity<?> getSettings() {
        return ResponseEntity.ok(settingsService.getSettings());
    }

    /**
     * PUT /api/admin/settings
     * Updates system settings
     * 
     * Request Body Example:
     * {
     *   "aiConfig": {
     *     "savingsThreshold": 1500,
     *     "budgetAlertPercentage": 85
     *   },
     *   "locationRules": {
     *     "enableCityBased": true,
     *     "priorityCities": ["Indore", "Mumbai", "Delhi"]
     *   }
     * }
     */
    @PutMapping
    public ResponseEntity<?> updateSettings(@RequestBody Map<String, Object> settings) {
        return ResponseEntity.ok(settingsService.updateSettings(settings));
    }

    /**
     * POST /api/admin/settings/reset
     * Resets all settings to default values
     */
    @PostMapping("/reset")
    public ResponseEntity<?> resetToDefaults() {
        return ResponseEntity.ok(settingsService.resetToDefaults());
    }
}

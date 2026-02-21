package com.example.demo.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminSettingsService {

    // In a real application, these would be stored in a database
    // For now, using in-memory storage

    private Map<String, Object> settings = new HashMap<>();

    public AdminSettingsService() {
        // Initialize default settings
        initializeDefaultSettings();
    }

    // ========== GET SETTINGS ==========
    public Map<String, Object> getSettings() {
        return new HashMap<>(settings); // Return copy to prevent modification
    }

    // ========== UPDATE SETTINGS ==========
    public Map<String, Object> updateSettings(Map<String, Object> newSettings) {
        // Update only provided settings
        if (newSettings.containsKey("aiConfig")) {
            settings.put("aiConfig", newSettings.get("aiConfig"));
        }
        if (newSettings.containsKey("locationRules")) {
            settings.put("locationRules", newSettings.get("locationRules"));
        }
        if (newSettings.containsKey("currency")) {
            settings.put("currency", newSettings.get("currency"));
        }
        if (newSettings.containsKey("security")) {
            settings.put("security", newSettings.get("security"));
        }

        return getSettings();
    }

    // ========== INITIALIZE DEFAULT SETTINGS ==========
    private void initializeDefaultSettings() {
        // AI Configuration
        Map<String, Object> aiConfig = new HashMap<>();
        aiConfig.put("savingsThreshold", 1000.0);
        aiConfig.put("budgetAlertPercentage", 80);
        aiConfig.put("enableHighImpact", true);
        aiConfig.put("enableMediumImpact", true);
        aiConfig.put("enableLowImpact", false);
        aiConfig.put("locationAware", true);
        settings.put("aiConfig", aiConfig);

        // Location Rules
        Map<String, Object> locationRules = new HashMap<>();
        locationRules.put("enableCityBased", true);
        locationRules.put("enableStateBased", true);
        locationRules.put("enableLocalVendors", true);
        List<String> priorityCities = Arrays.asList(
            "Indore", "Mumbai", "Bangalore", "Delhi", "Pune"
        );
        locationRules.put("priorityCities", priorityCities);
        settings.put("locationRules", locationRules);

        // Currency Settings
        Map<String, Object> currency = new HashMap<>();
        currency.put("default", "INR");
        currency.put("symbol", "₹");
        currency.put("format", "en-IN");
        currency.put("showDecimals", false);
        settings.put("currency", currency);

        // Security Settings
        Map<String, Object> security = new HashMap<>();
        security.put("twoFactorAuth", true);
        security.put("logAdminActivities", true);
        security.put("userDataEncryption", true);
        security.put("sessionTimeout", 30);
        settings.put("security", security);
    }

    // ========== RESET TO DEFAULTS ==========
    public Map<String, Object> resetToDefaults() {
        initializeDefaultSettings();
        return getSettings();
    }
}
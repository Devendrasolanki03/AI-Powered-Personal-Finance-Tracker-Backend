package com.example.demo.service;

import java.util.*;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.AiInsight;
import com.example.demo.entity.InsightType;
import com.example.demo.repository.AiInsightRepository;

@Service
@Transactional
public class AdminInsightsService {

    private final AiInsightRepository insightRepository;

    public AdminInsightsService(AiInsightRepository insightRepository) {
        this.insightRepository = insightRepository;
    }

    // ========== GET ALL INSIGHTS WITH FILTERING ==========
    public Page<Map<String, Object>> getAllInsights(
            Pageable pageable, String type, String impact) {
        
        Page<AiInsight> insights;

        // Filter by type if provided
        if (type != null && !type.isEmpty()) {
            try {
                InsightType insightType = InsightType.valueOf(type);
                insights = insightRepository.findByInsightType(insightType, pageable);
            } catch (IllegalArgumentException e) {
                // If invalid type, return all
                insights = insightRepository.findAll(pageable);
            }
        } else {
            insights = insightRepository.findAll(pageable);
        }

        // Map to DTO
        return insights.map(this::mapToDTO);
    }

    // ========== GET INSIGHTS STATISTICS ==========
    public Map<String, Object> getInsightsStats() {
        Map<String, Object> stats = new HashMap<>();

        // Count by impact (this is a simplified version)
        // You would need to add an 'impact' field to AiInsight entity
        // For now, returning total counts
        
        long totalInsights = insightRepository.count();
        
        // Count by type
        long savingsCount = insightRepository.countByInsightType(InsightType.SAVINGS);
        long budgetCount = insightRepository.countByInsightType(InsightType.BUDGET);
        long alertCount = insightRepository.countByInsightType(InsightType.ALERT);

        stats.put("totalInsights", totalInsights);
        stats.put("highImpact", Math.round(totalInsights * 0.15)); // 15% high impact
        stats.put("mediumImpact", Math.round(totalInsights * 0.55)); // 55% medium impact
        stats.put("lowImpact", Math.round(totalInsights * 0.30)); // 30% low impact

        // By type
        Map<String, Long> byType = new HashMap<>();
        byType.put("SAVINGS", savingsCount);
        byType.put("BUDGET", budgetCount);
        byType.put("ALERT", alertCount);
        stats.put("byType", byType);

        return stats;
    }

    // ========== MAP TO DTO ==========
    private Map<String, Object> mapToDTO(AiInsight insight) {
        Map<String, Object> dto = new HashMap<>();
        
        dto.put("insightId", insight.getInsightId());
        dto.put("insightText", insight.getInsightText());
        dto.put("insightType", insight.getInsightType().name());
        dto.put("createdAt", insight.getCreatedAt());
        
        // User information
        if (insight.getUser() != null) {
            dto.put("userName", insight.getUser().getName());
            dto.put("userEmail", insight.getUser().getEmail());
            
            // Location
            String location = "";
            if (insight.getUser().getCity() != null && insight.getUser().getState() != null) {
                location = insight.getUser().getCity() + ", " + insight.getUser().getState();
            }
            dto.put("location", location);
        }

        // Impact level (you can add logic to determine this)
        // For now, using a simple heuristic
        String impact = determineImpact(insight.getInsightType());
        dto.put("impact", impact);

        return dto;
    }

    // ========== DETERMINE IMPACT LEVEL ==========
    private String determineImpact(InsightType type) {
        // Simple heuristic - you can enhance this
        switch (type) {
            case ALERT:
                return "HIGH";
            case SAVINGS:
                return "MEDIUM";
            case BUDGET:
                return "MEDIUM";
            default:
                return "LOW";
        }
    }
}
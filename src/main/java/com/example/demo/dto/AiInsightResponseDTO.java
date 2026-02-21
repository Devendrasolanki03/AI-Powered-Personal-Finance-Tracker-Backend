package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.entity.InsightType;

/**
 * Data Transfer Object for sending AI Insights to the frontend.
 * Uses Java Record for immutability and clean JSON serialization.
 */
public record AiInsightResponseDTO(
        Long insightId,
        String insightText,
        InsightType insightType,
        LocalDateTime createdAt
) {}

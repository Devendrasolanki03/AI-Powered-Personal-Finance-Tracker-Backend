package com.example.demo.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.AiFinanceService;
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:5173",
    "https://ai-powered-finance-tracker.netlify.app"
})
@RestController
@RequestMapping("/api/ai")
public class AiController {

    private static final Logger log = LoggerFactory.getLogger(AiController.class);

    private final AiFinanceService aiService;

    public AiController(AiFinanceService aiService) {
        this.aiService = aiService;
    }

    // ✅ AI Advice Endpoint (Safe)
    @GetMapping("/advice")
    public ResponseEntity<String> advice(Principal principal) {
        try {
            String username = principal.getName();
            String advice = aiService.generateAdvice(username);
            return ResponseEntity.ok(advice);

        } catch (Exception ex) {
            log.error("AI advice failed for user", ex);

            // Fallback message instead of 500
            return ResponseEntity.ok(
                "AI insights are temporarily unavailable. Please try again later."
            );
        }
    }

    // ✅ AI Chat Endpoint (Safe)
    @PostMapping("/chat")
    public ResponseEntity<String> chat(
            @RequestBody String query,
            Principal principal) {

        try {
            String username = principal.getName();
            String response = aiService.chat(username, query);
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            log.error("AI chat failed for user", ex);

            return ResponseEntity.ok(
                "AI chat service is temporarily unavailable."
            );
        }
    }
}

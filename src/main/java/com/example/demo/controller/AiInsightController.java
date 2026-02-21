//package com.example.demo.controller;
//
//import java.security.Principal;
//import java.util.List;
//
//import org.springframework.web.bind.annotation.*;
//
//import com.example.demo.dto.AiInsightResponseDTO;
//import com.example.demo.entity.InsightType;
//import com.example.demo.service.AiInsightService;
//
//@RestController
//@RequestMapping("/api/ai-insights")
//@CrossOrigin(origins = "http://localhost:3000")
//public class AiInsightController {
//
//    private final AiInsightService aiInsightService;
//
//    public AiInsightController(AiInsightService aiInsightService) {
//        this.aiInsightService = aiInsightService;
//    }
//
//    // ================= CREATE =================
//    @PostMapping
//    public AiInsightResponseDTO createInsight(
//            Principal principal,
//            @RequestParam InsightType insightType,
//            @RequestBody String prompt) {
//
//        return aiInsightService.saveInsight(
//                principal.getName(),   // email from JWT
//                prompt,
//                insightType
//        );
//    }
//
//    // ================= GET ALL =================
//    @GetMapping
//    public List<AiInsightResponseDTO> getInsights(Principal principal) {
//        return aiInsightService.getInsightsByUser(principal.getName());
//    }
//
//    // ================= GET BY TYPE =================
//    @GetMapping("/type/{type}")
//    public List<AiInsightResponseDTO> getByType(
//            Principal principal,
//            @PathVariable String type) {
//
//        InsightType insightType = InsightType.valueOf(type.toUpperCase());
//
//        return aiInsightService.getInsightsByType(
//                principal.getName(),
//                insightType
//        );
//    }
//
//    // ================= DELETE =================
//    @DeleteMapping("/{insightId}")
//    public String deleteInsight(
//            @PathVariable Long insightId,
//            Principal principal) {
//
//        aiInsightService.deleteInsight(insightId, principal.getName());
//        return "AI insight deleted successfully";
//    }
//}



package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.AiInsightResponseDTO;
import com.example.demo.entity.InsightType;
import com.example.demo.service.AiInsightService;

@RestController
@RequestMapping("/api/ai-insights")
@CrossOrigin(origins = "http://localhost:3000")
public class AiInsightController {

    private final AiInsightService aiInsightService;

    public AiInsightController(AiInsightService aiInsightService) {
        this.aiInsightService = aiInsightService;
    }

    // ================= CREATE =================
    // ✅ FIXED: Using saveInsightDirect (no AI call, just saves to DB)
    @PostMapping
    public AiInsightResponseDTO createInsight(
            Principal principal,
            @RequestParam InsightType insightType,
            @RequestBody String prompt) {

        return aiInsightService.saveInsightDirect(
                principal.getName(),   // email from JWT
                prompt,                // text to save
                insightType
        );
    }

    // ================= GET ALL =================
    @GetMapping
    public List<AiInsightResponseDTO> getInsights(Principal principal) {
        return aiInsightService.getInsightsByUser(principal.getName());
    }

    // ================= GET BY TYPE =================
    @GetMapping("/type/{type}")
    public List<AiInsightResponseDTO> getByType(
            Principal principal,
            @PathVariable String type) {

        InsightType insightType = InsightType.valueOf(type.toUpperCase());

        return aiInsightService.getInsightsByType(
                principal.getName(),
                insightType
        );
    }

    // ================= DELETE =================
    @DeleteMapping("/{insightId}")
    public String deleteInsight(
            @PathVariable Long insightId,
            Principal principal) {

        aiInsightService.deleteInsight(insightId, principal.getName());
        return "AI insight deleted successfully";
    }
}
//package com.example.demo.service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.example.demo.dto.AiInsightResponseDTO;
//import com.example.demo.entity.AiInsight;
//import com.example.demo.entity.InsightType;
//import com.example.demo.entity.User;
//import com.example.demo.exception.ResourceNotFoundException;
//import com.example.demo.repository.AiInsightRepository;
//import com.example.demo.repository.UserRepository;
//
//@Service
//@Transactional
//public class AiInsightService {
//
//    private final AiInsightRepository insightRepo;
//    private final UserRepository userRepo;
//    private final ChatClient chatClient;
//
//    public AiInsightService(
//            AiInsightRepository insightRepo,
//            UserRepository userRepo,
//            ChatClient chatClient) {
//
//        this.insightRepo = insightRepo;
//        this.userRepo = userRepo;
//        this.chatClient = chatClient;
//    }
//
//    // ================= SAVE INSIGHT =================
//    @Transactional
//    public AiInsightResponseDTO saveInsight(
//            String email,
//            String userPrompt,
//            InsightType insightType) {
//
//        if (userPrompt == null || userPrompt.trim().isEmpty()) {
//            throw new IllegalArgumentException("Prompt cannot be empty");
//        }
//
//        User user = userRepo.findByEmail(email)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException(
//                                "User not found with email: " + email));
//
//        // 🔥 Build location context
//        String locationContext = buildLocationContext(user);
//
//        // 🔥 Call ChatGPT with location awareness
//        String aiResponse = chatClient
//                .prompt("""
//                    You are a professional Indian personal finance advisor.
//
//                    %s
//
//                    Give a short, clear, and actionable financial insight.
//
//                    User input:
//                    %s
//                    """.formatted(locationContext, userPrompt))
//                .call()
//                .content();
//
//        AiInsight insight = AiInsight.builder()
//                .insightText(aiResponse)
//                .insightType(insightType)
//                .user(user)
//                .build();
//
//        return mapToDTO(insightRepo.save(insight));
//    }
//
//    // ================= GET USER INSIGHTS =================
//    @Transactional(readOnly = true)
//    public List<AiInsightResponseDTO> getInsightsByUser(String email) {
//
//        User user = userRepo.findByEmail(email)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException(
//                                "User not found with email: " + email));
//
//        return insightRepo.findByUser_UserIdOrderByCreatedAtDesc(user.getUserId())
//                .stream()
//                .map(this::mapToDTO)
//                .collect(Collectors.toList());
//    }
//
//    // ================= FILTER BY TYPE =================
//    @Transactional(readOnly = true)
//    public List<AiInsightResponseDTO> getInsightsByType(
//            String email,
//            InsightType insightType) {
//
//        User user = userRepo.findByEmail(email)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException(
//                                "User not found with email: " + email));
//
//        return insightRepo
//                .findByUser_UserIdAndInsightTypeOrderByCreatedAtDesc(
//                        user.getUserId(), insightType)
//                .stream()
//                .map(this::mapToDTO)
//                .collect(Collectors.toList());
//    }
//
//    // ================= DELETE =================
//    @Transactional
//    public void deleteInsight(Long insightId, String email) {
//
//        User user = userRepo.findByEmail(email)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException(
//                                "User not found with email: " + email));
//
//        AiInsight insight = insightRepo
//                .findByInsightIdAndUser_UserId(insightId, user.getUserId())
//                .orElseThrow(() ->
//                        new ResourceNotFoundException(
//                                "Insight not found or not authorized"));
//
//        insightRepo.delete(insight);
//    }
//
//    // ================= LOCATION HELPER =================
//    private String buildLocationContext(User user) {
//
//        if (user.getCity() != null && user.getCountry() != null) {
//            return "User location: " + user.getCity() + ", " + user.getCountry()
//                    + ". Consider cost-of-living differences.";
//        }
//
//        return "User location is not specified. Give general Indian financial advice.";
//    }
//
// 
//
//    	private AiInsightResponseDTO mapToDTO(AiInsight insight) {
//
//    	    return new AiInsightResponseDTO(
//    	            insight.getInsightId(),
//    	            insight.getInsightText(),
//    	            insight.getInsightType(),
//    	            insight.getCreatedAt().atStartOfDay() // ✅ FIX
//    	    );
//    	}
//
//}


package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.AiInsightResponseDTO;
import com.example.demo.entity.AiInsight;
import com.example.demo.entity.InsightType;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AiInsightRepository;
import com.example.demo.repository.UserRepository;

@Service
@Transactional
public class AiInsightService {

    private static final Logger log = LoggerFactory.getLogger(AiInsightService.class);

    private final AiInsightRepository insightRepo;
    private final UserRepository userRepo;

    public AiInsightService(AiInsightRepository insightRepo, UserRepository userRepo) {
        this.insightRepo = insightRepo;
        this.userRepo = userRepo;
    }

    // ================= SAVE INSIGHT DIRECTLY (NO AI CALL) =================
    // ✅ NEW: This method just saves text to DB without calling AI
    // Used by AiFinanceService after AI generation
    @Transactional
    public AiInsightResponseDTO saveInsightDirect(
            String email,
            String insightText,
            InsightType insightType) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));

        AiInsight insight = AiInsight.builder()
                .insightText(insightText)
                .insightType(insightType)
                .user(user)
                .build();

        AiInsight saved = insightRepo.save(insight);
        log.info("Saved AI insight {} for user {}", saved.getInsightId(), email);

        return mapToDTO(saved);
    }

    // ================= GET USER INSIGHTS =================
    @Transactional(readOnly = true)
    public List<AiInsightResponseDTO> getInsightsByUser(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));

        return insightRepo.findByUser_UserIdOrderByCreatedAtDesc(user.getUserId())
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ================= FILTER BY TYPE =================
    @Transactional(readOnly = true)
    public List<AiInsightResponseDTO> getInsightsByType(String email, InsightType insightType) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));

        return insightRepo
                .findByUser_UserIdAndInsightTypeOrderByCreatedAtDesc(user.getUserId(), insightType)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ================= DELETE =================
    @Transactional
    public void deleteInsight(Long insightId, String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));

        AiInsight insight = insightRepo
                .findByInsightIdAndUser_UserId(insightId, user.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Insight not found or not authorized"));

        insightRepo.delete(insight);
        log.info("Deleted AI insight {} for user {}", insightId, email);
    }

    // ================= DTO MAPPER =================
    private AiInsightResponseDTO mapToDTO(AiInsight insight) {
        return new AiInsightResponseDTO(
                insight.getInsightId(),
                insight.getInsightText(),
                insight.getInsightType(),
                insight.getCreatedAt().atStartOfDay()
        );
    }
}

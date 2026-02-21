//
//package com.example.demo.service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.example.demo.entity.Expense;
//import com.example.demo.entity.InsightType;
//import com.example.demo.entity.User;
//import com.example.demo.exception.ResourceNotFoundException;
//import com.example.demo.repository.ExpenseRepository;
//import com.example.demo.repository.UserRepository;
//
//@Service
//@Transactional
//public class AiFinanceService {
//
//    private static final Logger log = LoggerFactory.getLogger(AiFinanceService.class);
//
//    private final ChatClient chatClient;
//    private final UserRepository userRepo;
//    private final ExpenseRepository expenseRepo;
//    private final AiInsightService insightService;
//
//    public AiFinanceService(ChatClient chatClient,
//                            UserRepository userRepo,
//                            ExpenseRepository expenseRepo,
//                            AiInsightService insightService) {
//
//        this.chatClient = chatClient;
//        this.userRepo = userRepo;
//        this.expenseRepo = expenseRepo;
//        this.insightService = insightService;
//    }
//
//    // ================== GENERATE AI ADVICE ==================
//    public String generateAdvice(String email) {
//
//        User user = getUserByEmail(email);
//        List<Expense> expenses = expenseRepo.findByUserWithCategory(user);
//
//        String locationContext = buildLocationContext(user);
//        String prompt = buildPrompt(expenses, locationContext);
//        String advice = callAi(prompt);
//
//        if (advice != null && !advice.isBlank()) {
//            insightService.saveInsight(
//                    email,
//                    advice,
//                    InsightType.ANALYSIS
//            );
//        }
//
//        return advice;
//    }
//
//    // ================== CHAT WITH AI ==================
//    public String chat(String email, String userQuery) {
//
//        if (userQuery == null || userQuery.trim().isEmpty()) {
//            throw new IllegalArgumentException("Query cannot be empty");
//        }
//
//        User user = getUserByEmail(email);
//
//        String locationContext = buildLocationContext(user);
//        String securedPrompt = """
//            %s
//
//            User question (personal finance related only):
//            %s
//            """.formatted(locationContext, userQuery);
//
//        String response = callAi(securedPrompt);
//
//        if (response != null && !response.isBlank()) {
//            insightService.saveInsight(
//                    email,
//                    response,
//                    InsightType.SAVINGS
//            );
//        }
//
//        return response;
//    }
//
//    // ================== COMMON AI CALL - FIXED ==================
//    // ✅ CHANGE: Returns fallback string instead of throwing exception
//    //    when OpenAI quota exceeded or any API error occurs
//    private String callAi(String prompt) {
//        try {
//            String response = chatClient.prompt()
//                    .system("""
//                        You are 'Dhan-Guru', a professional Indian personal finance advisor.
//                        Rules:
//                        - Use ₹ currency
//                        - Follow 50-30-20 budgeting rule
//                        - Adjust advice based on city if provided
//                        - Give only finance-related advice
//                        - Keep responses short, practical, and realistic
//                        """)
//                    .user(prompt)
//                    .call()
//                    .content();
//
//            if (response == null || response.isBlank()) {
//                log.warn("Empty response from AI, using fallback");
//                return getDefaultAdvice();
//            }
//
//            return response.trim();
//
//        } catch (Exception e) {
//            // ✅ FIXED: Log warning + return fallback (no more 500 errors!)
//            log.warn("AI service unavailable: {}. Using fallback advice.",
//                    e.getMessage() != null
//                            ? e.getMessage().substring(0, Math.min(120, e.getMessage().length()))
//                            : "unknown error");
//            return getDefaultAdvice();
//        }
//    }
//
//    // ✅ NEW: Fallback advice when OpenAI quota exceeded
//    private String getDefaultAdvice() {
//        return """
//                📊 AI insights are temporarily unavailable. Here are general tips from Dhan-Guru:
//                
//                • **Track every expense** — Even small purchases like chai and autorickshaw fares add up significantly over a month
//                • **Follow the 50-30-20 rule** — 50% needs (rent, groceries), 30% wants (dining, entertainment), 20% savings & investments
//                • **Review your top 3 spending categories** — Usually food, transport, and entertainment have the most room to cut back
//                • **Set a monthly budget** using the Budget feature and check alerts regularly to avoid overspending
//                • **Build an emergency fund** — Aim for 3-6 months of expenses in a liquid account before investing
//                """;
//    }
//
//    // ================== USER FETCH ==================
//    private User getUserByEmail(String email) {
//        return userRepo.findByEmail(email)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException(
//                                "User not found with email: " + email));
//    }
//
//    // ================== LOCATION CONTEXT ==================
//    private String buildLocationContext(User user) {
//        if (user.getCity() != null && user.getCountry() != null) {
//            return "User lives in " + user.getCity() + ", " + user.getCountry()
//                    + ". Consider local cost of living.";
//        }
//        return "User location not specified. Give general Indian financial advice.";
//    }
//
//    // ================== PROMPT BUILDER ==================
//    private String buildPrompt(List<Expense> expenses, String locationContext) {
//        if (expenses.isEmpty()) {
//            return """
//                %s
//
//                I have no expenses yet.
//                Guide me on budgeting for beginners in India.
//                """.formatted(locationContext);
//        }
//
//        String expenseData = expenses.stream()
//                .map(e -> String.format(
//                        "- %s: ₹%.2f (%s)",
//                        e.getCategory().getName(),
//                        e.getAmount(),
//                        e.getDescription() != null ? e.getDescription() : "No description"
//                ))
//                .collect(Collectors.joining("\n"));
//
//        return """
//            %s
//
//            My Expense Report:
//            %s
//
//            Please provide:
//            1. Overspending analysis
//            2. 3 cost-cutting tips
//            3. One monthly habit improvement
//            4. One financial warning
//            """.formatted(locationContext, expenseData);
//    }
//}


package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Expense;
import com.example.demo.entity.InsightType;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ExpenseRepository;
import com.example.demo.repository.UserRepository;

@Service
@Transactional
public class AiFinanceService {

    private static final Logger log = LoggerFactory.getLogger(AiFinanceService.class);

    private final ChatClient chatClient;
    private final UserRepository userRepo;
    private final ExpenseRepository expenseRepo;
    private final AiInsightService insightService;

    public AiFinanceService(ChatClient chatClient,
                            UserRepository userRepo,
                            ExpenseRepository expenseRepo,
                            AiInsightService insightService) {
        this.chatClient = chatClient;
        this.userRepo = userRepo;
        this.expenseRepo = expenseRepo;
        this.insightService = insightService;
    }

    // ================== GENERATE AI ADVICE ==================
    // ✅ This is called ONLY when user clicks "Generate New Advice" button
    public String generateAdvice(String email) {
        log.info("Generating AI advice for user: {}", email);

        User user = getUserByEmail(email);
        List<Expense> expenses = expenseRepo.findByUserWithCategory(user);

        String locationContext = buildLocationContext(user);
        String prompt = buildPrompt(expenses, locationContext);
        String advice = callGeminiAi(prompt);

        // ✅ Save to database for history
        if (advice != null && !advice.isBlank() && !advice.contains("temporarily unavailable")) {
            try {
                insightService.saveInsightDirect(email, advice, InsightType.ANALYSIS);
                log.info("AI advice saved successfully for user: {}", email);
            } catch (Exception e) {
                log.warn("Failed to save AI insight: {}", e.getMessage());
            }
        }

        return advice;
    }

    // ================== CHAT WITH AI ==================
    public String chat(String email, String userQuery) {
        if (userQuery == null || userQuery.trim().isEmpty()) {
            throw new IllegalArgumentException("Query cannot be empty");
        }

        User user = getUserByEmail(email);
        String locationContext = buildLocationContext(user);
        
        String securedPrompt = """
            %s

            User question (personal finance related only):
            %s
            
            Give a short, practical answer in 2-3 sentences.
            """.formatted(locationContext, userQuery);

        String response = callGeminiAi(securedPrompt);

        if (response != null && !response.isBlank() && !response.contains("temporarily unavailable")) {
            try {
                insightService.saveInsightDirect(email, response, InsightType.SAVINGS);
            } catch (Exception e) {
                log.warn("Failed to save chat insight: {}", e.getMessage());
            }
        }

        return response;
    }

    // ================== GEMINI AI CALL - WITH BETTER ERROR HANDLING ==================
    private String callGeminiAi(String prompt) {
        try {
            log.debug("Calling Gemini AI with prompt length: {}", prompt.length());

            String response = chatClient.prompt()
                    .system("""
                        You are 'Dhan-Guru', a professional Indian personal finance advisor powered by Google Gemini.
                        
                        Rules:
                        - Use ₹ currency symbol for all amounts
                        - Follow the 50-30-20 budgeting rule (50% needs, 30% wants, 20% savings)
                        - Adjust advice based on user's city and cost of living
                        - Give ONLY finance-related advice, politely decline other topics
                        - Keep responses short (max 250 words), practical, and actionable
                        - Use bullet points for clarity
                        - Focus on Indian financial context (SIPs, FDs, PPF, etc.)
                        - Be encouraging but realistic
                        """)
                    .user(prompt)
                    .call()
                    .content();

            if (response == null || response.isBlank()) {
                log.warn("Empty response from Gemini AI");
                return getFallbackAdvice();
            }

            log.info("Successfully received Gemini AI response");
            return response.trim();

        } catch (org.springframework.web.client.HttpClientErrorException e) {
            // 4xx errors (auth, rate limit, etc.)
            log.error("Gemini AI client error: {} - {}", e.getStatusCode(), e.getMessage());
            
            if (e.getStatusCode().value() == 429) {
                return "⚠️ AI quota exceeded. Please try again in a few minutes.";
            } else if (e.getStatusCode().value() == 401 || e.getStatusCode().value() == 403) {
                return "❌ AI service authentication failed. Please contact support.";
            }
            
            return getFallbackAdvice();

        } catch (org.springframework.web.client.HttpServerErrorException e) {
            // 5xx errors (server down, etc.)
            log.error("Gemini AI server error: {} - {}", e.getStatusCode(), e.getMessage());
            return "❌ AI service is temporarily down. Please try again later.";

        } catch (org.springframework.web.client.ResourceAccessException e) {
            // Network/timeout errors
            log.error("Gemini AI network error: {}", e.getMessage());
            return "❌ Network error connecting to AI service. Please check your connection.";

        } catch (Exception e) {
            // Any other unexpected error
            log.error("Unexpected error calling Gemini AI: {}", e.getMessage(), e);
            return getFallbackAdvice();
        }
    }

    // ================== FALLBACK ADVICE ==================
    private String getFallbackAdvice() {
        return """
                📊 **AI insights are temporarily unavailable**
                
                Here are general tips from Dhan-Guru while we reconnect:
                
                💡 **Smart Money Tips:**
                • Track every expense — Small purchases like chai & auto fares add up to ₹3,000-5,000/month
                • Follow 50-30-20 rule — 50% needs (rent, groceries), 30% wants (entertainment), 20% savings
                • Review top 3 categories — Usually food, transport & entertainment have most savings potential
                • Set monthly budgets — Use the Budget feature and check alerts to avoid overspending
                • Build emergency fund — Aim for 3-6 months expenses in liquid form before investing
                • Start SIP early — Even ₹1,000/month grows significantly over 5-10 years
                
                🎯 Try generating advice again in a few minutes for personalized insights!
                """;
    }

    // ================== USER FETCH ==================
    private User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with email: " + email));
    }

    // ================== LOCATION CONTEXT ==================
    private String buildLocationContext(User user) {
        if (user.getCity() != null && user.getCountry() != null) {
            return String.format(
                "User location: %s, %s. Consider local cost of living when giving advice.",
                user.getCity(), user.getCountry()
            );
        }
        return "User location not specified. Give general Indian financial advice.";
    }

    // ================== PROMPT BUILDER ==================
    private String buildPrompt(List<Expense> expenses, String locationContext) {
        if (expenses.isEmpty()) {
            return """
                %s

                This user has no expense records yet.
                
                Provide beginner-friendly budgeting advice for someone starting their financial journey in India.
                Include:
                - How to track expenses
                - Basic budgeting categories
                - Emergency fund importance
                - One actionable tip to start today
                
                Keep it encouraging and practical.
                """.formatted(locationContext);
        }

        // Build expense summary
        double totalSpent = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        String expenseData = expenses.stream()
                .limit(15) // Show top 15 expenses only
                .map(e -> String.format(
                        "• %s: ₹%.2f%s",
                        e.getCategory().getName(),
                        e.getAmount(),
                        e.getDescription() != null && !e.getDescription().isEmpty()
                                ? " (" + e.getDescription() + ")"
                                : ""
                ))
                .collect(Collectors.joining("\n"));

        return """
            %s

            **User's Recent Expenses (Total: ₹%.2f):**
            %s

            Analyze this data and provide:
            1. 🎯 Top spending insight (which category needs attention)
            2. 💰 2-3 specific cost-cutting tips
            3. ✅ One habit to improve this month
            4. ⚠️ One financial warning or risk to avoid
            
            Keep response under 250 words, use bullet points, be specific and actionable.
            """.formatted(
                locationContext,
                totalSpent,
                expenseData
            );
    }
}
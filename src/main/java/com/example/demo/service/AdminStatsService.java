//
//package com.example.demo.service;
//
//import java.time.LocalDate;
//import java.time.YearMonth;
//import java.util.*;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.example.demo.repository.UserRepository;
//import com.example.demo.repository.ExpenseRepository;
//import com.example.demo.repository.AiInsightRepository;
//
//@Service
//@Transactional
//public class AdminStatsService {
//
//    private final UserRepository userRepository;
//    private final ExpenseRepository expenseRepository;
//    private final AiInsightRepository aiInsightRepository;
//
//    public AdminStatsService(
//            UserRepository userRepository,
//            ExpenseRepository expenseRepository,
//            AiInsightRepository aiInsightRepository) {
//        this.userRepository = userRepository;
//        this.expenseRepository = expenseRepository;
//        this.aiInsightRepository = aiInsightRepository;
//    }
//
//    // ========== DASHBOARD STATS (4 CARDS) ==========
//    public Map<String, Object> getDashboardStats() {
//        Map<String, Object> stats = new HashMap<>();
//
//        // Total users
//        long totalUsers = userRepository.count();
//        stats.put("totalUsers", totalUsers);
//
//        // Total expenses (all users, all time)
//        Double totalExpenses = expenseRepository.getAllExpensesTotal();
//        stats.put("totalExpenses", totalExpenses != null ? totalExpenses : 0.0);
//
//        // Active users (users with expenses in last 30 days)
//        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
//        long activeUsers = userRepository.countActiveUsers(thirtyDaysAgo);
//        stats.put("activeUsers", activeUsers);
//
//        // AI Insights count
//        long aiInsights = aiInsightRepository.count();
//        stats.put("aiInsights", aiInsights);
//
//        // New users this month
//        YearMonth currentMonth = YearMonth.now();
//        LocalDate startOfMonth = currentMonth.atDay(1);
//        long newUsersThisMonth = userRepository.countUsersCreatedAfter(startOfMonth);
//        stats.put("newUsersThisMonth", newUsersThisMonth);
//
//        // Expenses this month
//        LocalDate endOfMonth = currentMonth.atEndOfMonth();
//        Double expensesThisMonth = expenseRepository.getExpensesBetween(startOfMonth, endOfMonth);
//        stats.put("expensesThisMonth", expensesThisMonth != null ? expensesThisMonth : 0.0);
//
//        // Active rate (percentage)
//        int activeRate = totalUsers > 0 ? (int) ((activeUsers * 100.0) / totalUsers) : 0;
//        stats.put("activeRate", activeRate);
//
//        // Insights this week
//        LocalDate weekAgo = LocalDate.now().minusDays(7);
//        long insightsThisWeek = aiInsightRepository.countInsightsAfter(weekAgo);
//        stats.put("insightsThisWeek", insightsThisWeek);
//
//        return stats;
//    }
//
//    // ========== MONTHLY EXPENSES CHART ==========
//    public List<Map<String, Object>> getMonthlyExpenses() {
//        int currentYear = LocalDate.now().getYear();
//        List<Map<String, Object>> monthlyData = new ArrayList<>();
//        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
//                          "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
//
//        for (int month = 1; month <= 12; month++) {
//            YearMonth yearMonth = YearMonth.of(currentYear, month);
//            LocalDate start = yearMonth.atDay(1);
//            LocalDate end = yearMonth.atEndOfMonth();
//
//            Double total = expenseRepository.getExpensesBetween(start, end);
//
//            Map<String, Object> data = new HashMap<>();
//            data.put("month", months[month - 1]);
//            data.put("total", total != null ? total : 0.0);
//
//            monthlyData.add(data);
//        }
//
//        return monthlyData;
//    }
//
//    // ========== CATEGORY DISTRIBUTION ==========
//    public List<Map<String, Object>> getCategoryDistribution() {
//        LocalDate startOfYear = LocalDate.of(LocalDate.now().getYear(), 1, 1);
//        LocalDate endOfYear = LocalDate.of(LocalDate.now().getYear(), 12, 31);
//
//        // Get category totals (pass null for email to get ALL users)
//        List<Object[]> categoryTotals = expenseRepository.getCategoryWiseExpenseSummary(
//            null, startOfYear, endOfYear
//        );
//
//        // Calculate total for percentages
//        double grandTotal = categoryTotals.stream()
//            .mapToDouble(row -> (Double) row[1])
//            .sum();
//
//        List<Map<String, Object>> categoryData = new ArrayList<>();
//        Map<String, String> categoryColors = getCategoryColors();
//
//        for (Object[] row : categoryTotals) {
//            String categoryName = (String) row[0];
//            Double amount = (Double) row[1];
//            int percentage = grandTotal > 0 ? (int) ((amount * 100.0) / grandTotal) : 0;
//
//            Map<String, Object> data = new HashMap<>();
//            data.put("name", categoryName);
//            data.put("value", percentage);
//            data.put("color", categoryColors.getOrDefault(categoryName, "#ef4444"));
//
//            categoryData.add(data);
//        }
//
//        // Sort by value descending
//        categoryData.sort((a, b) ->
//            Integer.compare((Integer) b.get("value"), (Integer) a.get("value"))
//        );
//
//        return categoryData;
//    }
//
//    // ========== HELPER: CATEGORY COLORS ==========
//    private Map<String, String> getCategoryColors() {
//        Map<String, String> colors = new HashMap<>();
//        colors.put("Food", "#a855f7");
//        colors.put("Transport", "#06b6d4");
//        colors.put("Shopping", "#8b5cf6");
//        colors.put("Utilities", "#22c55e");
//        colors.put("Entertainment", "#f59e0b");
//        colors.put("Healthcare", "#ec4899");
//        colors.put("Education", "#3b82f6");
//        return colors;
//    }
//}


package com.example.demo.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.UserRepository;
import com.example.demo.repository.ExpenseRepository;
import com.example.demo.repository.AiInsightRepository;

@Service
@Transactional
public class AdminStatsService {

    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final AiInsightRepository aiInsightRepository;

    public AdminStatsService(
            UserRepository userRepository,
            ExpenseRepository expenseRepository,
            AiInsightRepository aiInsightRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
        this.aiInsightRepository = aiInsightRepository;
    }

    // ========== DASHBOARD STATS (4 CARDS) ==========
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        long totalUsers = userRepository.count();
        stats.put("totalUsers", totalUsers);

        Double totalExpenses = expenseRepository.getAllExpensesTotal();
        stats.put("totalExpenses", totalExpenses != null ? totalExpenses : 0.0);

        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        long activeUsers = userRepository.countActiveUsers(thirtyDaysAgo);
        stats.put("activeUsers", activeUsers);

        long aiInsights = aiInsightRepository.count();
        stats.put("aiInsights", aiInsights);

        YearMonth currentMonth = YearMonth.now();
        LocalDate startOfMonth = currentMonth.atDay(1);
        long newUsersThisMonth = userRepository.countUsersCreatedAfter(startOfMonth);
        stats.put("newUsersThisMonth", newUsersThisMonth);

        LocalDate endOfMonth = currentMonth.atEndOfMonth();
        Double expensesThisMonth = expenseRepository.getExpensesBetween(startOfMonth, endOfMonth);
        stats.put("expensesThisMonth", expensesThisMonth != null ? expensesThisMonth : 0.0);

        int activeRate = totalUsers > 0 ? (int) ((activeUsers * 100.0) / totalUsers) : 0;
        stats.put("activeRate", activeRate);

        LocalDate weekAgo = LocalDate.now().minusDays(7);
        long insightsThisWeek = aiInsightRepository.countInsightsAfter(weekAgo);
        stats.put("insightsThisWeek", insightsThisWeek);

        return stats;
    }

    // ========== MONTHLY EXPENSES CHART ==========
    public List<Map<String, Object>> getMonthlyExpenses() {
        int currentYear = LocalDate.now().getYear();
        List<Map<String, Object>> monthlyData = new ArrayList<>();
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                          "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        for (int month = 1; month <= 12; month++) {
            YearMonth yearMonth = YearMonth.of(currentYear, month);
            LocalDate start = yearMonth.atDay(1);
            LocalDate end = yearMonth.atEndOfMonth();

            Double total = expenseRepository.getExpensesBetween(start, end);

            Map<String, Object> data = new HashMap<>();
            data.put("month", months[month - 1]);
            data.put("total", total != null ? total : 0.0);

            monthlyData.add(data);
        }

        return monthlyData;
    }

    // ========== CATEGORY DISTRIBUTION ==========
    public List<Map<String, Object>> getCategoryDistribution() {
        LocalDate startOfYear = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endOfYear = LocalDate.of(LocalDate.now().getYear(), 12, 31);

        // ✅ FIX: getCategoryWiseExpenseSummaryAllUsers - no email needed, all users ka data
        List<Object[]> categoryTotals = expenseRepository.getCategoryWiseExpenseSummaryAllUsers(
            startOfYear, endOfYear
        );

        double grandTotal = categoryTotals.stream()
            .mapToDouble(row -> (Double) row[1])
            .sum();

        List<Map<String, Object>> categoryData = new ArrayList<>();
        Map<String, String> categoryColors = getCategoryColors();

        for (Object[] row : categoryTotals) {
            String categoryName = (String) row[0];
            Double amount = (Double) row[1];
            int percentage = grandTotal > 0 ? (int) ((amount * 100.0) / grandTotal) : 0;

            Map<String, Object> data = new HashMap<>();
            data.put("name", categoryName);
            data.put("value", percentage);
            data.put("color", categoryColors.getOrDefault(categoryName, "#ef4444"));

            categoryData.add(data);
        }

        categoryData.sort((a, b) ->
            Integer.compare((Integer) b.get("value"), (Integer) a.get("value"))
        );

        return categoryData;
    }

    // ========== HELPER: CATEGORY COLORS ==========
    private Map<String, String> getCategoryColors() {
        Map<String, String> colors = new HashMap<>();
        colors.put("Food", "#a855f7");
        colors.put("Transport", "#06b6d4");
        colors.put("Shopping", "#8b5cf6");
        colors.put("Utilities", "#22c55e");
        colors.put("Entertainment", "#f59e0b");
        colors.put("Healthcare", "#ec4899");
        colors.put("Education", "#3b82f6");
        return colors;
    }
}
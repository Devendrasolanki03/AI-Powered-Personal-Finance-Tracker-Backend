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
//
//@Service
//@Transactional
//public class AdminAnalyticsService {
//
//    private final UserRepository userRepository;
//    private final ExpenseRepository expenseRepository;
//
//    public AdminAnalyticsService(
//            UserRepository userRepository,
//            ExpenseRepository expenseRepository) {
//        this.userRepository = userRepository;
//        this.expenseRepository = expenseRepository;
//    }
//
//    // ========== KPI METRICS ==========
//    public Map<String, Object> getKPIMetrics() {
//        Map<String, Object> kpi = new HashMap<>();
//
//        // Average expense per user
//        long totalUsers = userRepository.count();
//        Double totalExpenses = expenseRepository.getAllExpensesTotal();
//        double avgExpensePerUser = (totalUsers > 0 && totalExpenses != null) 
//            ? totalExpenses / totalUsers 
//            : 0.0;
//        
//        kpi.put("avgExpensePerUser", Math.round(avgExpensePerUser));
//
//        // Highest spending category
//        LocalDate startOfYear = LocalDate.of(LocalDate.now().getYear(), 1, 1);
//        LocalDate endOfYear = LocalDate.of(LocalDate.now().getYear(), 12, 31);
//        
//        List<Object[]> categoryTotals = expenseRepository.getCategoryWiseExpenseSummary(
//            null, startOfYear, endOfYear
//        );
//
//        String highestCategory = "N/A";
//        if (!categoryTotals.isEmpty()) {
//            highestCategory = (String) categoryTotals.get(0)[0];
//        }
//        kpi.put("highestCategory", highestCategory);
//
//        // Monthly growth (current month vs previous month)
//        YearMonth currentMonth = YearMonth.now();
//        YearMonth previousMonth = currentMonth.minusMonths(1);
//        
//        Double currentMonthExpenses = expenseRepository.getExpensesBetween(
//            currentMonth.atDay(1), currentMonth.atEndOfMonth()
//        );
//        
//        Double previousMonthExpenses = expenseRepository.getExpensesBetween(
//            previousMonth.atDay(1), previousMonth.atEndOfMonth()
//        );
//
//        double monthlyGrowth = 0.0;
//        if (previousMonthExpenses != null && previousMonthExpenses > 0 && currentMonthExpenses != null) {
//            monthlyGrowth = ((currentMonthExpenses - previousMonthExpenses) / previousMonthExpenses) * 100;
//        }
//        kpi.put("monthlyGrowth", Math.round(monthlyGrowth * 10.0) / 10.0);
//
//        // Budget utilization (placeholder - needs budget data)
//        kpi.put("budgetUtilization", 77.5);
//
//        return kpi;
//    }
//
//    // ========== CATEGORY ANALYTICS ==========
//    public List<Map<String, Object>> getCategoryAnalytics() {
//        LocalDate startOfYear = LocalDate.of(LocalDate.now().getYear(), 1, 1);
//        LocalDate endOfYear = LocalDate.of(LocalDate.now().getYear(), 12, 31);
//
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
//
//        for (Object[] row : categoryTotals) {
//            String categoryName = (String) row[0];
//            Double amount = (Double) row[1];
//            int percentage = grandTotal > 0 ? (int) ((amount * 100.0) / grandTotal) : 0;
//
//            Map<String, Object> data = new HashMap<>();
//            data.put("category", categoryName);
//            data.put("amount", amount);
//            data.put("percentage", percentage);
//            
//            categoryData.add(data);
//        }
//
//        // Sort by amount descending
//        categoryData.sort((a, b) -> 
//            Double.compare((Double) b.get("amount"), (Double) a.get("amount"))
//        );
//
//        return categoryData;
//    }
//
//    // ========== EXPENSE TRENDS ==========
//    public List<Map<String, Object>> getExpenseTrends(int year) {
//        List<Map<String, Object>> trendData = new ArrayList<>();
//        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
//                          "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
//
//        for (int month = 1; month <= 12; month++) {
//            YearMonth yearMonth = YearMonth.of(year, month);
//            LocalDate start = yearMonth.atDay(1);
//            LocalDate end = yearMonth.atEndOfMonth();
//
//            Double total = expenseRepository.getExpensesBetween(start, end);
//
//            Map<String, Object> data = new HashMap<>();
//            data.put("month", months[month - 1]);
//            data.put("expenses", total != null ? total : 0.0);
//
//            trendData.add(data);
//        }
//
//        return trendData;
//    }
//
//    // ========== SPENDING DISTRIBUTION ==========
//    public List<Map<String, Object>> getSpendingDistribution() {
//        // This is a simplified version
//        // You can enhance this by categorizing expenses into these groups
//        
//        List<Map<String, Object>> distribution = new ArrayList<>();
//
//        // Essential (Food, Utilities, Healthcare)
//        Map<String, Object> essential = new HashMap<>();
//        essential.put("name", "Essential");
//        essential.put("value", 42);
//        essential.put("color", "#4F46E5");
//        distribution.add(essential);
//
//        // Lifestyle (Shopping, Entertainment)
//        Map<String, Object> lifestyle = new HashMap<>();
//        lifestyle.put("name", "Lifestyle");
//        lifestyle.put("value", 28);
//        lifestyle.put("color", "#06b6d4");
//        distribution.add(lifestyle);
//
//        // Savings
//        Map<String, Object> savings = new HashMap<>();
//        savings.put("name", "Savings");
//        savings.put("value", 22);
//        savings.put("color", "#10b981");
//        distribution.add(savings);
//
//        // Investments
//        Map<String, Object> investments = new HashMap<>();
//        investments.put("name", "Investments");
//        investments.put("value", 8);
//        investments.put("color", "#f59e0b");
//        distribution.add(investments);
//
//        return distribution;
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

@Service
@Transactional
public class AdminAnalyticsService {

    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;

    public AdminAnalyticsService(
            UserRepository userRepository,
            ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    // ========== KPI METRICS ==========
    public Map<String, Object> getKPIMetrics() {
        Map<String, Object> kpi = new HashMap<>();

        long totalUsers = userRepository.count();
        Double totalExpenses = expenseRepository.getAllExpensesTotal();
        double avgExpensePerUser = (totalUsers > 0 && totalExpenses != null)
            ? totalExpenses / totalUsers
            : 0.0;
        kpi.put("avgExpensePerUser", Math.round(avgExpensePerUser));

        // ✅ FIX: All users category query
        LocalDate startOfYear = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endOfYear = LocalDate.of(LocalDate.now().getYear(), 12, 31);

        List<Object[]> categoryTotals = expenseRepository.getCategoryWiseExpenseSummaryAllUsers(
            startOfYear, endOfYear
        );

        String highestCategory = "N/A";
        if (!categoryTotals.isEmpty()) {
            highestCategory = (String) categoryTotals.get(0)[0];
        }
        kpi.put("highestCategory", highestCategory);

        YearMonth currentMonth = YearMonth.now();
        YearMonth previousMonth = currentMonth.minusMonths(1);

        Double currentMonthExpenses = expenseRepository.getExpensesBetween(
            currentMonth.atDay(1), currentMonth.atEndOfMonth()
        );
        Double previousMonthExpenses = expenseRepository.getExpensesBetween(
            previousMonth.atDay(1), previousMonth.atEndOfMonth()
        );

        double monthlyGrowth = 0.0;
        if (previousMonthExpenses != null && previousMonthExpenses > 0 && currentMonthExpenses != null) {
            monthlyGrowth = ((currentMonthExpenses - previousMonthExpenses) / previousMonthExpenses) * 100;
        }
        kpi.put("monthlyGrowth", Math.round(monthlyGrowth * 10.0) / 10.0);

        kpi.put("budgetUtilization", 77.5);

        return kpi;
    }

    // ========== CATEGORY ANALYTICS ==========
    public List<Map<String, Object>> getCategoryAnalytics() {
        LocalDate startOfYear = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endOfYear = LocalDate.of(LocalDate.now().getYear(), 12, 31);

        // ✅ FIX: All users category query
        List<Object[]> categoryTotals = expenseRepository.getCategoryWiseExpenseSummaryAllUsers(
            startOfYear, endOfYear
        );

        double grandTotal = categoryTotals.stream()
            .mapToDouble(row -> (Double) row[1])
            .sum();

        List<Map<String, Object>> categoryData = new ArrayList<>();

        for (Object[] row : categoryTotals) {
            String categoryName = (String) row[0];
            Double amount = (Double) row[1];
            int percentage = grandTotal > 0 ? (int) ((amount * 100.0) / grandTotal) : 0;

            Map<String, Object> data = new HashMap<>();
            data.put("category", categoryName);
            data.put("amount", amount);
            data.put("percentage", percentage);

            categoryData.add(data);
        }

        categoryData.sort((a, b) ->
            Double.compare((Double) b.get("amount"), (Double) a.get("amount"))
        );

        return categoryData;
    }

    // ========== EXPENSE TRENDS ==========
    public List<Map<String, Object>> getExpenseTrends(int year) {
        List<Map<String, Object>> trendData = new ArrayList<>();
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                          "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        for (int month = 1; month <= 12; month++) {
            YearMonth yearMonth = YearMonth.of(year, month);
            LocalDate start = yearMonth.atDay(1);
            LocalDate end = yearMonth.atEndOfMonth();

            Double total = expenseRepository.getExpensesBetween(start, end);

            Map<String, Object> data = new HashMap<>();
            data.put("month", months[month - 1]);
            data.put("expenses", total != null ? total : 0.0);

            trendData.add(data);
        }

        return trendData;
    }

    // ========== SPENDING DISTRIBUTION ==========
    public List<Map<String, Object>> getSpendingDistribution() {
        LocalDate startOfYear = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endOfYear = LocalDate.of(LocalDate.now().getYear(), 12, 31);

        // ✅ Real data from DB grouped by category type
        List<Object[]> categoryTotals = expenseRepository.getCategoryWiseExpenseSummaryAllUsers(
            startOfYear, endOfYear
        );

        double essential = 0, lifestyle = 0, total = 0;
        Set<String> essentialCats = Set.of("Food", "Utilities", "Healthcare");
        Set<String> lifestyleCats = Set.of("Shopping", "Entertainment", "Transport");

        for (Object[] row : categoryTotals) {
            String name = (String) row[0];
            Double amount = (Double) row[1];
            total += amount;
            if (essentialCats.contains(name)) essential += amount;
            else if (lifestyleCats.contains(name)) lifestyle += amount;
        }

        int essentialPct = total > 0 ? (int) ((essential * 100) / total) : 42;
        int lifestylePct = total > 0 ? (int) ((lifestyle * 100) / total) : 28;
        int otherPct = 100 - essentialPct - lifestylePct;

        List<Map<String, Object>> distribution = new ArrayList<>();

        Map<String, Object> e = new HashMap<>();
        e.put("name", "Essential"); e.put("value", essentialPct); e.put("color", "#4F46E5");
        distribution.add(e);

        Map<String, Object> l = new HashMap<>();
        l.put("name", "Lifestyle"); l.put("value", lifestylePct); l.put("color", "#06b6d4");
        distribution.add(l);

        Map<String, Object> o = new HashMap<>();
        o.put("name", "Other"); o.put("value", Math.max(otherPct, 0)); o.put("color", "#10b981");
        distribution.add(o);

        return distribution;
    }
}
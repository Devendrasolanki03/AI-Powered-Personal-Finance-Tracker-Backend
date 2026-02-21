package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.BudgetAlertDTO;
import com.example.demo.entity.Budget;
import com.example.demo.entity.Category;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BudgetRepository;
import com.example.demo.repository.ExpenseRepository;
import com.example.demo.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class BudgetAlertService {

    private final BudgetRepository budgetRepo;
    private final ExpenseRepository expenseRepo;
    private final UserRepository userRepo;

    public BudgetAlertService(BudgetRepository budgetRepo,
                               ExpenseRepository expenseRepo,
                               UserRepository userRepo) {
        this.budgetRepo = budgetRepo;
        this.expenseRepo = expenseRepo;
        this.userRepo = userRepo;
    }

    public List<BudgetAlertDTO> getBudgetAlerts(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // JOIN FETCH category to avoid LazyInitializationException
        List<Budget> budgets = budgetRepo.findByUserWithCategory(user);

        if (budgets.isEmpty()) return new ArrayList<>();

        LocalDate now          = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth   = now.withDayOfMonth(now.lengthOfMonth());

        // ✅ FIXED: Collect all category IDs first, then ONE batch query
        //    (was 10x individual queries before - one per budget)
        List<Long> categoryIds = budgets.stream()
                .filter(b -> b.getCategory() != null)
                .map(b -> b.getCategory().getCategoryId())
                .collect(Collectors.toList());

        // ✅ Single query for ALL categories at once
        List<Object[]> spendingRows = expenseRepo.getTotalExpenseByUserAndCategoriesAndMonth(
                email, categoryIds, startOfMonth, endOfMonth);

        // Convert to Map<categoryId, totalSpent> for O(1) lookup
        Map<Long, Double> spentMap = spendingRows.stream()
                .collect(Collectors.toMap(
                        row -> ((Number) row[0]).longValue(),
                        row -> ((Number) row[1]).doubleValue()
                ));

        // Build alert DTOs (same logic as before)
        List<BudgetAlertDTO> alerts = new ArrayList<>();

        for (Budget budget : budgets) {

            Category category = budget.getCategory();
            if (category == null) continue;

            Long   categoryId   = category.getCategoryId();
            String categoryName = category.getName();

            // ✅ Lookup from map instead of querying DB each time
            double spentAmount = spentMap.getOrDefault(categoryId, 0.0);
            double limit       = budget.getMonthlyLimit() != null ? budget.getMonthlyLimit() : 0.0;
            double percentage  = limit > 0 ? (spentAmount / limit) * 100 : 0;

            String status;
            String message;

            if (percentage >= 100) {
                status  = "EXCEEDED";
                message = "Budget exceeded! Spent Rs." + String.format("%.0f", spentAmount)
                        + " of Rs." + String.format("%.0f", limit)
                        + " (" + String.format("%.1f", percentage) + "%)";
            } else if (percentage >= 90) {
                status  = "CRITICAL";
                message = "Almost over budget! " + String.format("%.1f", percentage) + "% used";
            } else if (percentage >= 70) {
                status  = "WARNING";
                message = "Approaching limit. " + String.format("%.1f", percentage) + "% used";
            } else {
                status  = "SAFE";
                message = "Within budget. " + String.format("%.1f", percentage) + "% used";
            }

            alerts.add(new BudgetAlertDTO(
                    budget.getBudgetId(),
                    categoryId,
                    categoryName,
                    limit,
                    spentAmount,
                    percentage,
                    status,
                    message
            ));
        }

        return alerts;
    }

    public List<BudgetAlertDTO> getActiveAlerts(String email) {
        return getBudgetAlerts(email).stream()
                .filter(a -> a.getStatus().equals("EXCEEDED") ||
                             a.getStatus().equals("CRITICAL") ||
                             a.getStatus().equals("WARNING"))
                .toList();
    }
}
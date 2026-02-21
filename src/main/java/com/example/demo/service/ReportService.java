package com.example.demo.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Income;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ExpenseRepository;  // ✅ ADD THIS
import com.example.demo.repository.IncomeRepository;

@Service
@Transactional(readOnly = true)
public class ReportService {

    private final IncomeRepository incomeRepo;
    private final ExpenseRepository expenseRepo;  // ✅ ADD THIS

    // ✅ ADD expenseRepo to constructor
    public ReportService(IncomeRepository incomeRepo, ExpenseRepository expenseRepo) {
        this.incomeRepo = incomeRepo;
        this.expenseRepo = expenseRepo;
    }

    // ================= MONTHLY INCOME =================
    public List<Income> getMonthlyIncome(String email, YearMonth month) {
        if (email == null || email.isBlank()) {
            throw new InvalidRequestException("Email is required");
        }
        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();
        List<Income> list = incomeRepo.findMonthlyIncome(email, start, end);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No income found for this month");
        }
        return list;
    }

    public Map<String, Double> getMonthlyIncomeSummary(String email, YearMonth month) {
        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();
        Double total = incomeRepo.getMonthlyTotalIncome(email, start, end);
        if (total == null) total = 0.0;

        Map<String, Double> map = new HashMap<>();
        map.put("month", (double) month.getMonthValue());
        map.put("totalIncome", total);
        return map;
    }

    // ================= YEARLY INCOME =================
    public List<Income> getYearlyIncome(String email, int year) {
        return incomeRepo.findYearlyIncome(email, year);
    }

    public Map<String, Double> getYearlyIncomeSummary(String email, int year) {
        Double total = incomeRepo.getYearlyTotalIncome(email, year);
        if (total == null) total = 0.0;

        Map<String, Double> map = new HashMap<>();
        map.put("year", (double) year);
        map.put("totalIncome", total);
        return map;
    }

    public Map<String, Double> getYearlyMonthlyIncomeChart(String email, int year) {
        List<Object[]> data = incomeRepo.getYearlyMonthlyIncomeChart(email, year);
        Map<String, Double> chart = new LinkedHashMap<>();
        for (Object[] row : data) {
            Integer month = (Integer) row[0];
            Double amount = (Double) row[1];
            chart.put("Month " + month, amount);
        }
        return chart;
    }

    // ================= EXPENSE REPORTS =================

    // ✅ Category-wise expense summary between dates
    public Map<String, Double> getExpensesBetweenDates(String email, LocalDate start, LocalDate end) {
        List<Object[]> results = expenseRepo.getCategoryWiseExpenseSummary(email, start, end);
        Map<String, Double> summary = new LinkedHashMap<>();
        for (Object[] row : results) {
            String category = (String) row[0];
            Double amount = ((Number) row[1]).doubleValue();
            summary.put(category, amount);
        }
        return summary;
    }

    // ✅ Same as above (used by category-summary endpoint)
    public Map<String, Double> getCategoryWiseSummary(String email, LocalDate start, LocalDate end) {
        return getExpensesBetweenDates(email, start, end);
    }

    // ✅ Monthly expense chart for the year
    public Map<String, Double> getYearlyMonthlyExpenseChart(String email, int year) {
        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        Map<String, Double> chart = new LinkedHashMap<>();
        for (String m : months) chart.put(m, 0.0);

        List<Object[]> results = expenseRepo.getMonthlyExpenseChart(email, year);
        for (Object[] row : results) {
            int monthNum = ((Number) row[0]).intValue();
            Double amount = ((Number) row[1]).doubleValue();
            chart.put(months[monthNum - 1], amount);
        }
        return chart;
    }
}
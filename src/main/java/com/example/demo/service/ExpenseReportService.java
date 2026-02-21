package com.example.demo.service;

import java.time.*;
import java.util.*;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Hibernate;
import com.example.demo.dto.ExpenseResponseDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Expense;
import com.example.demo.repository.ExpenseRepository;

@Service
@Transactional(readOnly = true)
public class ExpenseReportService {

    private final ExpenseRepository expenseRepo;

    public ExpenseReportService(ExpenseRepository expenseRepo) {
        this.expenseRepo = expenseRepo;
    }

    public List<ExpenseResponseDTO> getWeeklyExpenseDTO(String email, LocalDate date) {

        LocalDate start = date.with(java.time.temporal.TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = date.with(java.time.temporal.TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        return expenseRepo.findExpensesBetweenDates(email, start, end)
                .stream().map(this::mapToDTO).toList();
    }

    public List<ExpenseResponseDTO> getMonthlyExpenseDTO(String email, YearMonth month) {
        return expenseRepo.findExpensesBetweenDates(
                email,
                month.atDay(1),
                month.atEndOfMonth()
        ).stream().map(this::mapToDTO).toList();
    }

    public List<ExpenseResponseDTO> getYearlyExpenseDTO(String email, int year) {
        return expenseRepo.findExpensesBetweenDates(
                email,
                LocalDate.of(year, 1, 1),
                LocalDate.of(year, 12, 31)
        ).stream().map(this::mapToDTO).toList();
    }

    public List<ExpenseResponseDTO> getExpensesByCategoryDTO(String email, String category,
                                                             LocalDate start, LocalDate end) {
        return expenseRepo.findExpensesByCategoryBetweenDates(email, category, start, end)
                .stream().map(this::mapToDTO).toList();
    }

    public Map<String, Double> getCategorySummary(String email, LocalDate start, LocalDate end) {
        Map<String, Double> map = new LinkedHashMap<>();
        for (Object[] row : expenseRepo.getCategoryWiseExpenseSummary(email, start, end)) {
            map.put((String) row[0], (Double) row[1]);
        }
        return map;
    }

   

    private ExpenseResponseDTO mapToDTO(Expense e) {

        ExpenseResponseDTO dto = new ExpenseResponseDTO();
        dto.setExpenseId(e.getExpenseId());
        dto.setAmount(e.getAmount());
        dto.setDescription(e.getDescription());
        dto.setExpenseDate(e.getExpenseDate());

        if (Hibernate.isInitialized(e.getCategory()) && e.getCategory() != null) {
            Category c = e.getCategory();
            dto.setCategoryId(c.getCategoryId());
            dto.setCategoryName(c.getName());
            dto.setCategoryType(c.getType().name());

        }

        return dto;
    }
    public void deleteExpense(Long id) {
        if (!expenseRepo.existsById(id)) {
            throw new RuntimeException("Expense not found with id: " + id);
        }
        expenseRepo.deleteById(id);
    }

}

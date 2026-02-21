package com.example.demo.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ExpenseResponseDTO;
import com.example.demo.service.ExpenseReportService;
import com.example.demo.service.ReportService;

@RestController
@RequestMapping("/api/expense-report")
@CrossOrigin(origins = "http://localhost:3000")
public class ExpenseReportController {

    private final ExpenseReportService expenseReportService;
    private final ReportService reportService; // if you really need this

    public ExpenseReportController(ExpenseReportService expenseReportService,
                                   ReportService reportService) {
        this.expenseReportService = expenseReportService;
        this.reportService = reportService;
    }

    // ================= WEEKLY =================
    @GetMapping("/weekly")
    public List<ExpenseResponseDTO> getWeeklyExpenses(
            @RequestParam String email,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return expenseReportService.getWeeklyExpenseDTO(email, date);
    }

    // ================= MONTHLY =================
    @GetMapping("/monthly")
    public List<ExpenseResponseDTO> getMonthlyExpenses(
            @RequestParam String email,
            @RequestParam int year,
            @RequestParam int month) {

        return expenseReportService.getMonthlyExpenseDTO(email, YearMonth.of(year, month));
    }

    // ================= YEARLY =================
    @GetMapping("/yearly")
    public List<ExpenseResponseDTO> getYearlyExpenses(
            @RequestParam String email,
            @RequestParam int year) {

        return expenseReportService.getYearlyExpenseDTO(email, year);
    }

    // ================= CATEGORY FILTER =================
    @GetMapping("/category")
    public List<ExpenseResponseDTO> getExpensesByCategory(
            @RequestParam String email,
            @RequestParam String category,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return expenseReportService.getExpensesByCategoryDTO(email, category, startDate, endDate);
    }

    // ================= CATEGORY SUMMARY (CHART) =================
    @GetMapping("/category-summary")
    public Map<String, Double> getCategorySummary(
            @RequestParam String email,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return expenseReportService.getCategorySummary(email, startDate, endDate);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {

    	expenseReportService.deleteExpense(id);

        return ResponseEntity.ok("Expense deleted successfully");
    }
}

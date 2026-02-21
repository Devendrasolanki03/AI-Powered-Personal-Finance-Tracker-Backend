//
//package com.example.demo.controller;
//
//import java.time.YearMonth;
//import java.util.List;
//import java.util.Map;
//
//
//import org.springframework.web.bind.annotation.*;
//
//import com.example.demo.entity.Income;
//import com.example.demo.service.ReportService;
//
//@RestController
//@RequestMapping("/api/reports")
//@CrossOrigin(origins = "http://localhost:3000")
//public class ReportController {
//
//    private final ReportService reportService;
//
//    public ReportController(ReportService reportService) {
//        this.reportService = reportService;
//    }
//
//    // ================= MONTHLY =================
//    @GetMapping("/monthly-income")
//    public List<Income> getMonthlyIncome(
//            @RequestParam String email,
//            @RequestParam int year,
//            @RequestParam int month) {
//
//        YearMonth ym = YearMonth.of(year, month);
//        return reportService.getMonthlyIncome(email, ym);
//    }
//
//    @GetMapping("/monthly-income-summary")
//    public Map<String, Double> getMonthlyIncomeSummary(
//            @RequestParam String email,
//            @RequestParam int year,
//            @RequestParam int month) {
//
//        YearMonth ym = YearMonth.of(year, month);
//        return reportService.getMonthlyIncomeSummary(email, ym);
//    }
//
//    // ================= YEARLY =================
//    
//    @GetMapping("/yearly-income")
//    public List<Income> getYearlyIncome(
//            @RequestParam String email,
//            @RequestParam int year) {
//
//        return reportService.getYearlyIncome(email, year);
//    }
//
//    @GetMapping("/yearly-income-summary")
//    public Map<String, Double> getYearlyIncomeSummary(
//            @RequestParam String email,
//            @RequestParam int year) {
//
//        return reportService.getYearlyIncomeSummary(email, year);
//    }
//
//    // ================= YEARLY MONTH-WISE CHART =================
//    @GetMapping("/yearly-income-chart")
//    public Map<String, Double> getYearlyIncomeChart(
//            @RequestParam String email,
//            @RequestParam int year) {
//
//        return reportService.getYearlyMonthlyIncomeChart(email, year);
//    }
//}

package com.example.demo.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Income;
import com.example.demo.service.ReportService;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:3000")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // ================= INCOME =================
    @GetMapping("/monthly-income")
    public List<Income> getMonthlyIncome(
            @RequestParam String email,
            @RequestParam int year,
            @RequestParam int month) {
        return reportService.getMonthlyIncome(email, YearMonth.of(year, month));
    }

    @GetMapping("/monthly-income-summary")
    public Map<String, Double> getMonthlyIncomeSummary(
            @RequestParam String email,
            @RequestParam int year,
            @RequestParam int month) {
        return reportService.getMonthlyIncomeSummary(email, YearMonth.of(year, month));
    }

    @GetMapping("/yearly-income")
    public List<Income> getYearlyIncome(
            @RequestParam String email,
            @RequestParam int year) {
        return reportService.getYearlyIncome(email, year);
    }

    @GetMapping("/yearly-income-summary")
    public Map<String, Double> getYearlyIncomeSummary(
            @RequestParam String email,
            @RequestParam int year) {
        return reportService.getYearlyIncomeSummary(email, year);
    }

    @GetMapping("/yearly-income-chart")
    public Map<String, Double> getYearlyIncomeChart(
            @RequestParam String email,
            @RequestParam int year) {
        return reportService.getYearlyMonthlyIncomeChart(email, year);
    }

    // ================= EXPENSE REPORTS (NEW) =================

    // ✅ Weekly expenses - uses Principal (secure, no email in param)
    @GetMapping("/weekly-expenses")
    public Map<String, Double> getWeeklyExpenses(
            Principal principal,
            @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        // Get start and end of week
        LocalDate start = localDate.minusDays(localDate.getDayOfWeek().getValue() % 7);
        LocalDate end = start.plusDays(6);
        return reportService.getExpensesBetweenDates(principal.getName(), start, end);
    }

    // ✅ Monthly expenses
    @GetMapping("/monthly-expenses")
    public Map<String, Double> getMonthlyExpenses(
            Principal principal,
            @RequestParam int year,
            @RequestParam int month) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();
        return reportService.getExpensesBetweenDates(principal.getName(), start, end);
    }

    // ✅ Yearly expenses
    @GetMapping("/yearly-expenses")
    public Map<String, Double> getYearlyExpenses(
            Principal principal,
            @RequestParam int year) {
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);
        return reportService.getExpensesBetweenDates(principal.getName(), start, end);
    }

    // ✅ Category breakdown
    @GetMapping("/category-summary")
    public Map<String, Double> getCategorySummary(
            Principal principal,
            @RequestParam String start,
            @RequestParam String end) {
        return reportService.getCategoryWiseSummary(
                principal.getName(),
                LocalDate.parse(start),
                LocalDate.parse(end)
        );
    }
}
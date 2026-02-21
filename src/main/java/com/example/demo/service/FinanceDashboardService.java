package com.example.demo.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.ExpenseRepository;
import com.example.demo.repository.IncomeRepository;

@Service
@Transactional
public class FinanceDashboardService {

	private final IncomeRepository incomeRepo;
	private final ExpenseRepository expenseRepo;

	public FinanceDashboardService(IncomeRepository incomeRepo, ExpenseRepository expenseRepo) {
		this.incomeRepo = incomeRepo;
		this.expenseRepo = expenseRepo;
	}

	public Map<String, Object> getDashboard(String email, int year) {

		Double totalIncome = incomeRepo.getTotalIncome(email);
		Double totalExpense = expenseRepo.getTotalExpense(email);
		Double savings = totalIncome - totalExpense;

		Map<String, Double> incomeChart = convertMonthData(incomeRepo.getYearlyMonthlyIncomeChart(email, year));

		Map<String, Double> expenseChart = convertMonthData(expenseRepo.getMonthlyExpenseChart(email, year));

		Map<String, Double> categoryChart = new LinkedHashMap<>();
		expenseRepo.getCategoryWiseExpenseSummary(email, LocalDate.of(year, 1, 1), LocalDate.of(year, 12, 31))
				.forEach(row -> categoryChart.put((String) row[0], (Double) row[1]));

		Map<String, Object> res = new HashMap<>();
		res.put("totalIncome", totalIncome);
		res.put("totalExpense", totalExpense);
		res.put("savings", savings);
		res.put("monthlyIncomeChart", incomeChart);
		res.put("monthlyExpenseChart", expenseChart);
		res.put("categoryExpenseChart", categoryChart);

		return res;
	}

	private Map<String, Double> convertMonthData(List<Object[]> data) {
		String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		Map<String, Double> map = new LinkedHashMap<>();
		for (Object[] row : data) {
			int m = (int) row[0];
			map.put(months[m - 1], (Double) row[1]);
		}
		return map;
	}
}

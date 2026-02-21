package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ExpenseRequestDTO;
import com.example.demo.dto.ExpenseResponseDTO;
import com.example.demo.dto.IncomeDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Expense;
import com.example.demo.entity.Income;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ExpenseRepository;
import com.example.demo.repository.IncomeRepository;
import com.example.demo.repository.UserRepository;

@Service
@Transactional
public class FinanceService {

	private final UserRepository userRepo;
	private final IncomeRepository incomeRepo;
	private final ExpenseRepository expenseRepo;
	private final CategoryRepository categoryRepo;

	public FinanceService(UserRepository userRepo, IncomeRepository incomeRepo, ExpenseRepository expenseRepo,
			CategoryRepository categoryRepo) {
		this.userRepo = userRepo;
		this.incomeRepo = incomeRepo;
		this.expenseRepo = expenseRepo;
		this.categoryRepo = categoryRepo;
	}

	// ---------------- ADD INCOME ----------------
	public Income addIncome(String email, IncomeDTO dto) {

		User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Income income = new Income();
		income.setAmount(dto.getAmount());
		income.setSource(dto.getSource());
		income.setIncomeDate(dto.getIncomeDate());
		income.setUser(user);

		return incomeRepo.save(income);
	}
	
	
	public String deleteIncome(String username, Long incomeId) {

	    Income income = incomeRepo.findById(incomeId)
	            .orElseThrow(() -> new ResourceNotFoundException("Income not found with id: " + incomeId));

	    // Optional Security Check (Recommended)
	    if (!income.getUser().getEmail().equals(username)) {
	        throw new RuntimeException("Unauthorized to delete this income");
	    }

	    incomeRepo.delete(income);
	    return "Income deleted successfully";
	}


	
	// ---------------- ADD EXPENSE ----------------
	public Expense addExpense(String email, ExpenseRequestDTO dto) {

		User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Category category = categoryRepo.findById(dto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));

		Expense expense = new Expense();
		expense.setAmount(dto.getAmount());
		expense.setCategory(category);
		expense.setDescription(dto.getDescription());
		expense.setExpenseDate(dto.getExpenseDate());
		expense.setUser(user);

		return expenseRepo.save(expense);
	}

	// ---------------- GET EXPENSES ----------------
	public List<ExpenseResponseDTO> getExpenses(String email) {

		User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

		return expenseRepo.findByUserWithCategory(user).stream().map(this::mapToDTO).toList();
	}

	// ---------------- ENTITY → DTO ----------------
	private ExpenseResponseDTO mapToDTO(Expense e) {

		ExpenseResponseDTO dto = new ExpenseResponseDTO();
		dto.setExpenseId(e.getExpenseId());
		dto.setAmount(e.getAmount());
		dto.setDescription(e.getDescription());
		dto.setExpenseDate(e.getExpenseDate());

		if (e.getCategory() != null) {
			dto.setCategoryId(e.getCategory().getCategoryId());
			dto.setCategoryName(e.getCategory().getName());
			dto.setCategoryType(e.getCategory().getType().name());
		}

		return dto;
	}

	public List<IncomeDTO> getIncomes(String email) {

	    User user = userRepo.findByEmail(email)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

	    return incomeRepo.findByUser(user)
	            .stream()
	            .map(income -> new IncomeDTO(
	                    income.getIncomeId(),          // ⭐ MOST IMPORTANT
	                    income.getAmount(),
	                    income.getSource(),
	                    income.getIncomeDate()))
	            .collect(Collectors.toList());
	}

}

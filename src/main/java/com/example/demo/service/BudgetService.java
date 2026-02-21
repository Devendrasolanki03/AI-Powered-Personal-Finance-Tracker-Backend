
package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.BudgetDTO;
import com.example.demo.entity.Budget;
import com.example.demo.entity.Category;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BudgetRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.UserRepository;

@Service
@Transactional
public class BudgetService {

    private final BudgetRepository budgetRepo;
    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;

    public BudgetService(BudgetRepository budgetRepo,
                         UserRepository userRepo,
                         CategoryRepository categoryRepo) {
        this.budgetRepo = budgetRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    // CREATE
    public Budget addBudget(String email, BudgetDTO dto) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Budget budget = new Budget();
        budget.setCategory(category);
        budget.setMonthlyLimit(dto.getMonthlyLimit());
        budget.setUser(user);

        return budgetRepo.save(budget);
    }

    // READ
    @Transactional(readOnly = true)
    public List<Budget> getBudgetsByUser(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return budgetRepo.findByUser(user);
    }

    // UPDATE
    public Budget updateBudget(Long budgetId, String email, BudgetDTO dto) {
        Budget budget = budgetRepo.findByIdWithUser(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found"));

        if (!budget.getUser().getEmail().equals(email)) {
            throw new ResourceNotFoundException("Unauthorized");
        }

        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        budget.setCategory(category);
        budget.setMonthlyLimit(dto.getMonthlyLimit());

        return budgetRepo.save(budget);
    }

    // DELETE
    public void deleteBudget(Long budgetId, String email) {
        Budget budget = budgetRepo.findByIdWithUser(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found"));

        if (!budget.getUser().getEmail().equals(email)) {
            throw new ResourceNotFoundException("Unauthorized");
        }

        budgetRepo.delete(budget);
    }
}
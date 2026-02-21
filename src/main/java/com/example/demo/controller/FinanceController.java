package com.example.demo.controller;

import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ExpenseRequestDTO;
import com.example.demo.dto.ExpenseResponseDTO;
import com.example.demo.dto.IncomeDTO;
import com.example.demo.service.FinanceService;

@RestController
@RequestMapping("/api/finance")
@CrossOrigin(origins = "http://localhost:3000")
public class FinanceController {

    private final FinanceService financeService;

    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    // ---------------- ADD INCOME ----------------
    @PostMapping("/income")
    public ResponseEntity<?> addIncome(
            Principal principal,
            @Valid @RequestBody IncomeDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(financeService.addIncome(principal.getName(), dto));
    }

    // ✅ ADD THIS - GET ALL INCOMES
    @GetMapping("/incomes")
    public ResponseEntity<?> getIncomes(Principal principal) {
        return ResponseEntity.ok(
                financeService.getIncomes(principal.getName())
        );
    }

    
 // ---------------- DELETE INCOME ----------------
    @DeleteMapping("/income/{incomeId}")
    public ResponseEntity<?> deleteIncome(
            @PathVariable Long incomeId,
            Principal principal) {

        return ResponseEntity.ok(
                financeService.deleteIncome(principal.getName(), incomeId)
        );
    }

    
    
    // ---------------- ADD EXPENSE ----------------
    @PostMapping("/expense")
    public ResponseEntity<?> addExpense(
            Principal principal,
            @Valid @RequestBody ExpenseRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(financeService.addExpense(principal.getName(), dto));
    }

    // ---------------- GET EXPENSES ----------------
    @GetMapping("/expenses")
    public ResponseEntity<List<ExpenseResponseDTO>> getExpenses(Principal principal) {

        return ResponseEntity.ok(
                financeService.getExpenses(principal.getName())
        );
    }
    
}
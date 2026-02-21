package com.example.demo.dto;

import java.time.LocalDate;

public class ExpenseResponseDTO {

    private Long expenseId;
    private Double amount;
    private String description;
    private LocalDate expenseDate;
    private Long categoryId;
    private String categoryName;
    private String categoryType;

    public ExpenseResponseDTO() {}

    public ExpenseResponseDTO(Long expenseId, Double amount, String description,
                              LocalDate expenseDate, Long categoryId,
                              String categoryName, String categoryType) {
        this.expenseId = expenseId;
        this.amount = amount;
        this.description = description;
        this.expenseDate = expenseDate;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

	public Long getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(LocalDate expenseDate) {
		this.expenseDate = expenseDate;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

    // getters and setters...
}

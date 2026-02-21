package com.example.demo.dto;



import java.time.LocalDate;

public class ExpenseRequestDTO {

    private Double amount;
    private Long categoryId;
    private String description;
    private LocalDate expenseDate;
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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
	public ExpenseRequestDTO(Double amount, Long categoryId, String description, LocalDate expenseDate) {
		super();
		this.amount = amount;
		this.categoryId = categoryId;
		this.description = description;
		this.expenseDate = expenseDate;
	}
	public ExpenseRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


    
    
}

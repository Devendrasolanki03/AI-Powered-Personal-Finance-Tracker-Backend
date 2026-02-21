package com.example.demo.dto;

public class BudgetAlertDTO {

    private Long budgetId;
    private Long categoryId;
    private String categoryName;
    private Double monthlyLimit;
    private Double currentSpent;
    private Double percentage;
    private String status;   // SAFE / WARNING / CRITICAL / EXCEEDED
    private String message;

    public BudgetAlertDTO() {}

    public BudgetAlertDTO(Long budgetId, Long categoryId, String categoryName,
                           Double monthlyLimit, Double currentSpent,
                           Double percentage, String status, String message) {
        this.budgetId = budgetId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.monthlyLimit = monthlyLimit;
        this.currentSpent = currentSpent;
        this.percentage = percentage;
        this.status = status;
        this.message = message;
    }

    public Long getBudgetId() { return budgetId; }
    public void setBudgetId(Long budgetId) { this.budgetId = budgetId; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public Double getMonthlyLimit() { return monthlyLimit; }
    public void setMonthlyLimit(Double monthlyLimit) { this.monthlyLimit = monthlyLimit; }

    public Double getCurrentSpent() { return currentSpent; }
    public void setCurrentSpent(Double currentSpent) { this.currentSpent = currentSpent; }

    public Double getPercentage() { return percentage; }
    public void setPercentage(Double percentage) { this.percentage = percentage; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
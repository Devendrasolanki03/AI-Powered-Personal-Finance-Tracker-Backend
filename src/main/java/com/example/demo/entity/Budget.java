package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "budget")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long budgetId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Double monthlyLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;



    public Budget() {
    }



	public Long getBudgetId() {
		return budgetId;
	}



	public void setBudgetId(Long budgetId) {
		this.budgetId = budgetId;
	}



	public Category getCategory() {
		return category;
	}



	public void setCategory(Category category) {
		this.category = category;
	}



	public Double getMonthlyLimit() {
		return monthlyLimit;
	}



	public void setMonthlyLimit(Double monthlyLimit) {
		this.monthlyLimit = monthlyLimit;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public Budget(Long budgetId, Category category, Double monthlyLimit, User user) {
		super();
		this.budgetId = budgetId;
		this.category = category;
		this.monthlyLimit = monthlyLimit;
		this.user = user;
	}

    
}

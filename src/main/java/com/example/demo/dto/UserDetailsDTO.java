package com.example.demo.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsDTO {
    private Long userId;
    private String name;
    private String email;
    private String city;
    private String state;
    private String country;
    private String status; // ACTIVE or BLOCKED
    private Double totalExpenses;
    private Double totalIncome;
    private Integer expenseCount;
    private Integer incomeCount;
}
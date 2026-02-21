package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Budget;
import com.example.demo.entity.User;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByUserUserId(Long userId);

    Long countByUser_UserId(Long userId);

    boolean existsByUserAndCategory_CategoryId(User user, Long categoryId);

    // ✅ Simple find by user
    List<Budget> findByUser(User user);

    // ✅ JOIN FETCH category - avoids lazy loading on category
    @Query("SELECT b FROM Budget b JOIN FETCH b.category WHERE b.user = :user")
    List<Budget> findByUserWithCategory(@Param("user") User user);

    // ✅ JOIN FETCH user - avoids LazyInitializationException on user in update/delete
    @Query("SELECT b FROM Budget b JOIN FETCH b.user WHERE b.budgetId = :budgetId")
    Optional<Budget> findByIdWithUser(@Param("budgetId") Long budgetId);
}
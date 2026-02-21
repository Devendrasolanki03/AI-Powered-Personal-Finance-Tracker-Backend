package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Expense;
import com.example.demo.entity.User;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // ================= GET ALL EXPENSES WITH CATEGORY =================
    @Query("""
                SELECT DISTINCT e FROM Expense e
                JOIN FETCH e.category c
                WHERE e.user = :user
            """)
    List<Expense> findByUserWithCategory(@Param("user") User user);

    // ================= RANGE QUERY (FETCH CATEGORY + USER) =================
    @Query("""
                SELECT DISTINCT e FROM Expense e
                JOIN FETCH e.category c
                JOIN e.user u
                WHERE u.email = :email
                AND e.expenseDate BETWEEN :start AND :end
            """)
    List<Expense> findExpensesBetweenDates(@Param("email") String email, @Param("start") LocalDate start,
            @Param("end") LocalDate end);

    // ================= RANGE + CATEGORY =================
    @Query("""
                SELECT DISTINCT e FROM Expense e
                JOIN FETCH e.category c
                JOIN e.user u
                WHERE u.email = :email
                AND c.name = :category
                AND e.expenseDate BETWEEN :start AND :end
            """)
    List<Expense> findExpensesByCategoryBetweenDates(@Param("email") String email, @Param("category") String category,
            @Param("start") LocalDate start, @Param("end") LocalDate end);

    // ================= TOTAL EXPENSE =================
    @Query("""
                SELECT COALESCE(SUM(e.amount),0)
                FROM Expense e
                JOIN e.user u
                WHERE u.email = :email
                AND e.expenseDate BETWEEN :start AND :end
            """)
    Double getTotalExpenseBetweenDates(@Param("email") String email, @Param("start") LocalDate start,
            @Param("end") LocalDate end);

    @Query("SELECT SUM(e.amount) FROM Expense e")
    Double getAllExpensesTotal();

    @Query("SELECT SUM(e.amount) FROM Expense e " + "WHERE e.expenseDate BETWEEN :start AND :end")
    Double getExpensesBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    // ================= CATEGORY SUMMARY =================
    @Query("""
                SELECT c.name, COALESCE(SUM(e.amount),0)
                FROM Expense e
                JOIN e.category c
                JOIN e.user u
                WHERE u.email = :email
                AND e.expenseDate BETWEEN :start AND :end
                GROUP BY c.name
                ORDER BY SUM(e.amount) DESC
            """)
    List<Object[]> getCategoryWiseExpenseSummary(@Param("email") String email, @Param("start") LocalDate start,
            @Param("end") LocalDate end);

    // TOTAL EXPENSE
    @Query("SELECT COALESCE(SUM(e.amount),0) FROM Expense e WHERE e.user.email = :email")
    Double getTotalExpense(@Param("email") String email);

    // MONTH-WISE EXPENSE CHART
    @Query("""
                SELECT MONTH(e.expenseDate), SUM(e.amount)
                FROM Expense e
                WHERE e.user.email = :email AND YEAR(e.expenseDate) = :year
                GROUP BY MONTH(e.expenseDate)
                ORDER BY MONTH(e.expenseDate)
            """)
    List<Object[]> getMonthlyExpenseChart(@Param("email") String email, @Param("year") int year);

    // ================= ADMIN AGGREGATE QUERIES =================

    @Query("SELECT COALESCE(SUM(e.amount),0) FROM Expense e")
    Double getTotalExpenseAllUsers();

    @Query("""
                SELECT COALESCE(SUM(e.amount),0)
                FROM Expense e
                WHERE MONTH(e.expenseDate) = MONTH(CURRENT_DATE)
                  AND YEAR(e.expenseDate) = YEAR(CURRENT_DATE)
            """)
    Double getExpensesThisMonth();

    @Query("""
                SELECT MONTH(e.expenseDate), COALESCE(SUM(e.amount),0)
                FROM Expense e
                GROUP BY MONTH(e.expenseDate)
                ORDER BY MONTH(e.expenseDate)
            """)
    List<Object[]> getMonthlyExpenseChartAllUsers();

    @Query("""
                SELECT c.name, COALESCE(SUM(e.amount),0)
                FROM Expense e
                JOIN e.category c
                WHERE e.expenseDate BETWEEN :start AND :end
                GROUP BY c.name
                ORDER BY SUM(e.amount) DESC
            """)
    List<Object[]> getCategoryWiseExpenseSummaryAllUsers(@Param("start") LocalDate start,
            @Param("end") LocalDate end);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user.userId = :userId")
    Double getTotalExpensesByUserId(@Param("userId") Long userId);

    Long countByUser_UserId(Long userId);

    // ================= BUDGET ALERT - SINGLE CATEGORY =================
    @Query("""
                SELECT COALESCE(SUM(e.amount), 0)
                FROM Expense e
                JOIN e.user u
                JOIN e.category c
                WHERE u.email = :email
                AND c.categoryId = :categoryId
                AND e.expenseDate BETWEEN :start AND :end
            """)
    Double getTotalExpenseByUserAndCategoryAndMonth(
            @Param("email") String email,
            @Param("categoryId") Long categoryId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end);

    // ✅ NEW: BUDGET ALERT - BATCH QUERY (all categories at once, fixes 10x query problem)
    @Query("""
                SELECT c.categoryId, COALESCE(SUM(e.amount), 0)
                FROM Expense e
                JOIN e.user u
                JOIN e.category c
                WHERE u.email = :email
                AND c.categoryId IN :categoryIds
                AND e.expenseDate BETWEEN :start AND :end
                GROUP BY c.categoryId
            """)
    List<Object[]> getTotalExpenseByUserAndCategoriesAndMonth(
            @Param("email") String email,
            @Param("categoryIds") List<Long> categoryIds,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end);
}
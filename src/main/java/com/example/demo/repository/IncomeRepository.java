package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Income;
import com.example.demo.entity.User;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findByUser(User user);

    // ================= MONTHLY =================
    @Query("SELECT i FROM Income i WHERE i.user.email = :email AND i.incomeDate BETWEEN :start AND :end")
    List<Income> findMonthlyIncome(
            @Param("email") String email,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query("SELECT COALESCE(SUM(i.amount),0) FROM Income i WHERE i.user.email = :email AND i.incomeDate BETWEEN :start AND :end")
    Double getMonthlyTotalIncome(
            @Param("email") String email,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    // ================= YEARLY =================
    @Query("SELECT i FROM Income i WHERE i.user.email = :email AND YEAR(i.incomeDate) = :year")
    List<Income> findYearlyIncome(
            @Param("email") String email,
            @Param("year") int year
    );

    @Query("SELECT COALESCE(SUM(i.amount),0) FROM Income i WHERE i.user.email = :email AND YEAR(i.incomeDate) = :year")
    Double getYearlyTotalIncome(
            @Param("email") String email,
            @Param("year") int year
    );

    // ================= MONTH-WISE YEARLY CHART =================
    @Query("""
        SELECT MONTH(i.incomeDate), SUM(i.amount)
        FROM Income i
        WHERE i.user.email = :email AND YEAR(i.incomeDate) = :year
        GROUP BY MONTH(i.incomeDate)
        ORDER BY MONTH(i.incomeDate)
    """)
    List<Object[]> getYearlyMonthlyIncomeChart(
            @Param("email") String email,
            @Param("year") int year
    );

    // ✅ TOTAL INCOME FOR DASHBOARD
    @Query("SELECT COALESCE(SUM(i.amount),0) FROM Income i WHERE i.user.email = :email")
    Double getTotalIncome(@Param("email") String email);
}

package com.example.demo.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByProviderAndProviderId(String provider, String providerId);

    // ✅ Active users count (users who have expenses after a date)
    @Query("SELECT COUNT(DISTINCT e.user) FROM Expense e WHERE e.expenseDate >= :sinceDate")
    long countActiveUsers(@Param("sinceDate") LocalDate sinceDate);

    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt >= :date")
    long countUsersCreatedAfter(@Param("date") LocalDate date);

    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt <= :date")
    long countUsersCreatedBefore(@Param("date") LocalDate date);

    long countByStatus(String status);

    // ✅ Search by name OR email (case-insensitive) - KEPT ONLY THIS ONE (removed duplicate)
    Page<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String name, String email, Pageable pageable);

    long countByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);

    Page<User> findByStatus(String status, Pageable pageable);

    // ✅ Search + status filter
    @Query("SELECT u FROM User u WHERE " +
           "(LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
           "u.status = :status")
    Page<User> searchByNameOrEmailAndStatus(
            @Param("search") String search,
            @Param("status") String status,
            Pageable pageable);
}
package com.example.demo.repository;



import com.example.demo.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    // Latest unused OTP for email + purpose
    @Query("""
        SELECT o FROM Otp o
        WHERE o.email = :email
        AND o.purpose = :purpose
        AND o.used = false
        AND o.expiresAt > :now
        ORDER BY o.createdAt DESC
        LIMIT 1
    """)
    Optional<Otp> findLatestValid(
        @Param("email") String email,
        @Param("purpose") String purpose,
        @Param("now") LocalDateTime now
    );

    // Delete expired OTPs (cleanup)
    @Modifying
    @Transactional
    @Query("DELETE FROM Otp o WHERE o.expiresAt < :now OR o.used = true")
    void deleteExpiredOtps(@Param("now") LocalDateTime now);

    // Delete all OTPs for email (on successful verify)
    @Modifying
    @Transactional
    void deleteByEmail(String email);
}
package com.example.demo.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ai_insights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiInsight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long insightId;

    // ✅ Keep columnDefinition = "TEXT" - this is correct for MySQL
    // The alter table problem is caused by ddl-auto=update, NOT this annotation
    @Column(columnDefinition = "TEXT", nullable = false)
    private String insightText;

    @Enumerated(EnumType.STRING)
    private InsightType insightType;

    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;

    // ================= RELATIONSHIPS =================

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDate.now();
        }
    }
}
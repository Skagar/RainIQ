package com.rainiq.designservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "designs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Design {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id ;

    @Column(nullable = false, name = "user_email")
    private String userEmail;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private BigDecimal area;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    private void create()
    {
        createdAt=LocalDateTime.now();
    }

    @PreUpdate
    private void update()
    {
        updatedAt=LocalDateTime.now();
    }
}

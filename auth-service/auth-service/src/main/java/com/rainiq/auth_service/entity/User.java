package com.rainiq.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,length = 100)
    private String name;

    @Column(nullable = false,unique = true,length = 150)
    private String email;

    @Column(length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(name = "oauth_provider",length = 50)
    private String oauthProvider;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist //AutoRuns before first save of User
    protected void onCreate()
    {
        createdAt=LocalDateTime.now();
    }
}

package com.rainiq.notificationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "design_id",nullable = false)
    private UUID designId;

    @Column(name = "property_id",nullable = false)
    private UUID propertyId;

    @Column(name = "recommendation_id")
    private UUID recommendationId;

    @Column(name = "recipient_email",nullable = false)
    private String recipientEmail;

    @Column(name = "event_type",nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @Column(name = "failure_reason")
    private String failureReason;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void create()
    {
        createdAt=LocalDateTime.now();
    }

}

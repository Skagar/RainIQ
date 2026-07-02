package com.rainiq.monitoringservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tank_reading")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TankReading {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "device_id",nullable = false)
    private String deviceId;

    @Column(name = "property_id",nullable = false)
    private UUID propertyId;

    @Column(name = "tank_level_percent",nullable = false)
    private BigDecimal tankLevelPercent;

    @Column(name = "recorded_at",nullable = false)
    private LocalDateTime recordedAt;

    @PrePersist
    private void recorded()
    {
        recordedAt=LocalDateTime.now();
    }
}

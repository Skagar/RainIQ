package com.rainiq.monitoringservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "monitoring_device")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonitoringDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "device_id",nullable = false,unique = true)
    private String deviceId;

    @Column(name = "property_id",nullable = false)
    private UUID propertyId;

    @Column(name ="design_id",nullable = false)
    private UUID designId;

    @Column(name = "installed_by",nullable = false)
    private String installedBy;

    @Column(name = "installed_at",nullable = false)
    private LocalDateTime installedAt;

    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private MonitoringStatus status;

    @PrePersist
    private void installed()
    {
        installedAt=LocalDateTime.now();
    }
}

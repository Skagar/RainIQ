package com.rainiq.complianceservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "compliance_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplianceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,name = "design_id")
    private UUID designId;

    @Column(nullable = false,name = "property_id")
    private UUID propertyId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "compliance_status")
    private ComplianceStatus complianceStatus;

    @Column
    private String reason;

    @Column(name = "calculated_capacity",nullable = false)
    private BigDecimal calculatedCapacity;

    @Column(name = "recommended_area",nullable = false)
    private BigDecimal recommendedArea ;

    @Column(name = "checked_at",nullable = false)
    private LocalDateTime checkedAt;

    @PrePersist
    private void check()
    {
        checkedAt=LocalDateTime.now();
    }
}

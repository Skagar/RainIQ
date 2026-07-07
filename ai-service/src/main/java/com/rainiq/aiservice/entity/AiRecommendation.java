package com.rainiq.aiservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ai_recommendation")
public class AiRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "design_id",nullable = false,unique = true)
    private UUID designId;

    @Column(name = "property_id",nullable = false)
    private UUID propertyId;

    @Column(name = "recommended_tank_size_liters")
    private Integer recommendedTankSizeLiters;

    @Column(name = "recommended_pipe_spec")
    private String recommendedPipeSpec;

    @Column(name = "recommended_filtration_type")
    private String recommendedFiltrationType;

    @Column(name = "estimated_cost_inr")
    private BigDecimal estimatedCostInr;

    @Column(name = "estimated_annual_savings_inr")
    private BigDecimal estimatedAnnualSavingsInr;

    @Column(name = "comments")
    private String comments;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AiResponseStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    private void created()
    {
        createdAt=LocalDateTime.now();
    }

}

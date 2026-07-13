package com.rainiq.notificationservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiCompletedEvent {
    private UUID recommendationId;
    private UUID designId;
    private UUID propertyId;
    private Integer recommendedTankSizeLiters;
    private String recommendedPipeSpec;
    private String recommendedFiltrationType;
    private BigDecimal estimatedCostInr;
    private BigDecimal estimatedAnnualSavingsInr;
}

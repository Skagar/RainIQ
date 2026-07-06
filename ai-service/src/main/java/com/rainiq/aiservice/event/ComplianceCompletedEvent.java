package com.rainiq.aiservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplianceCompletedEvent {
    private UUID designId;
    private UUID propertyId;
    private BigDecimal calculatedCapacity;
    private BigDecimal recommendedArea;
}

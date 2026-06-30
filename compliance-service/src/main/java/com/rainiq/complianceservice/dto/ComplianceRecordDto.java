package com.rainiq.complianceservice.dto;

import com.rainiq.complianceservice.entity.ComplianceStatus;
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
public class ComplianceRecordDto {
    private UUID id;
    private UUID designId;
    private UUID propertyId;
    private ComplianceStatus complianceStatus;
    private String reason;
    private BigDecimal calculatedCapacity;
    private BigDecimal recommendedArea ;
    private LocalDateTime checkedAt;
}

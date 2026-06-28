package com.rainiq.complianceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyClientDto {
    private BigDecimal area;
    private String pincode;
    private String surfaceType;
}

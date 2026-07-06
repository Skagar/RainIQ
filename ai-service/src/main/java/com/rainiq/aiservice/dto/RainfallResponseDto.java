package com.rainiq.aiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RainfallResponseDto {
    private String pincode;
    private Double latitude;
    private Double longitude;
    private Double avgRainfall;
}


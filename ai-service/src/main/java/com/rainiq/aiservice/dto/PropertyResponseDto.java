package com.rainiq.aiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyResponseDto {
    private UUID id;
    private String city;
    private String state;
    private String pincode;
    private String propertyType;
    private String status;
    private String surfaceType;
}

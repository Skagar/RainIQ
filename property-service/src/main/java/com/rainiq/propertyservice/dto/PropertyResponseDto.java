package com.rainiq.propertyservice.dto;

import com.rainiq.propertyservice.entity.PropertyStatus;
import com.rainiq.propertyservice.entity.PropertyType;
import com.rainiq.propertyservice.entity.SurfaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyResponseDto {
    private UUID id;
    private String ownerEmail;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private BigDecimal area;
    private PropertyType propertyType;
    private PropertyStatus status;
    private SurfaceType surfaceType;
    private LocalDateTime createdAt;
    private  LocalDateTime updatedAt;

}

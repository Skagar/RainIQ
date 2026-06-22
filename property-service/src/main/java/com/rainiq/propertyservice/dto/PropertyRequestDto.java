package com.rainiq.propertyservice.dto;

import com.rainiq.propertyservice.entity.PropertyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyRequestDto {
    @NotBlank(message = "Address should not be empty")
    private String address;

    @NotBlank(message = "City should not be empty")
    private String city;

    @NotBlank(message = "State should not be empty")
    private String state;

    @NotBlank(message = "Pincode should not be empty")
    private String pincode;

    @NotNull(message = "Area should not be empty")
    private BigDecimal area;

    @NotNull(message = "Property type should not be empty")
    private PropertyType propertyType;
}

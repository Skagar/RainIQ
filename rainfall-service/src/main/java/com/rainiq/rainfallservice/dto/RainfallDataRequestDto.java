package com.rainiq.rainfallservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RainfallDataRequestDto {
    @NotBlank(message = "Pincode cannot be null")
    private String pincode;

}

package com.rainiq.designservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DesignRequest {
    @NotBlank(message = "Location can't be empty")
    private String location;
    @NotNull(message = "Area can't be empty")
    private BigDecimal area;
}


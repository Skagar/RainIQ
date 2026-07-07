package com.rainiq.aiservice.dto.gemini;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeminiRecommendationDto {
    private Integer recommendedTankSizeLiters;
    private String recommendedPipeSpec;
    private String recommendedFiltrationType;
    private BigDecimal estimatedCostInr;
    private BigDecimal estimatedAnnualSavingsInr;
}

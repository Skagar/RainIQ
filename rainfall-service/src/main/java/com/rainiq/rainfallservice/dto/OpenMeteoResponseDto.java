package com.rainiq.rainfallservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenMeteoResponseDto {
    @JsonProperty("daily")
    private DailyDataDto dailyDataDto;
}

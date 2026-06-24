package com.rainiq.rainfallservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyDataDto {
    @JsonProperty("precipitation_sum")
    private List<Double> precipitationSum;
}

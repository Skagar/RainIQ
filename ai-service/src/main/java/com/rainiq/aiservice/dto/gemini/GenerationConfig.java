package com.rainiq.aiservice.dto.gemini;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenerationConfig {
    @Builder.Default
    private String responseMimeType="application/json";
    private ResponseSchema responseSchema;
}

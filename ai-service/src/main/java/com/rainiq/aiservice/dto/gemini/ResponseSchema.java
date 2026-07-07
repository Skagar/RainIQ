package com.rainiq.aiservice.dto.gemini;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseSchema {
    @Builder.Default
    private String type="OBJECT";
    private Map<String,SchemaProperty> properties;
}

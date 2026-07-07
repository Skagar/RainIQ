package com.rainiq.aiservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainiq.aiservice.dto.gemini.*;
import com.rainiq.aiservice.exception.GeminiResponseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GeminiClient {
    @Value("${gemini.api.key}")
    private String  apikey;
    @Value("${gemini.model}")
    private String model;
    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    public GeminiClient(@Value("${gemini.base.url}") String baseUrl,ObjectMapper objectMapper)
    {
        this.restClient=RestClient.builder().baseUrl(baseUrl).build();
        this.objectMapper=objectMapper;
    }

    public GeminiRecommendationDto generateRecommendation(String prompt) throws JsonProcessingException {
        Part part= Part.builder()
                        .text(prompt).build();
        List<Part>parts=new ArrayList<>();
        parts.add(part);
        Content content=Content.builder()
                .parts(parts)
                .build();
        List<Content>contents=new ArrayList<>();
        contents.add(content);
        Map<String,SchemaProperty> map=new HashMap<>();
        SchemaProperty schemaProperty1=SchemaProperty.builder().type("INTEGER").build();
        SchemaProperty schemaProperty2=SchemaProperty.builder().type("STRING").build();
        SchemaProperty schemaProperty3=SchemaProperty.builder().type("STRING").build();
        SchemaProperty schemaProperty4=SchemaProperty.builder().type("NUMBER").build();
        SchemaProperty schemaProperty5=SchemaProperty.builder().type("NUMBER").build();
        map.put("recommendedTankSizeLiters",schemaProperty1);
        map.put("recommendedPipeSpec",schemaProperty2);
        map.put("recommendedFiltrationType",schemaProperty3);
        map.put("estimatedCostInr",schemaProperty4);
        map.put("estimatedAnnualSavingsInr",schemaProperty5);
        ResponseSchema responseSchema=ResponseSchema.
                builder().
                properties(map).
                build();
        GenerationConfig generationConfig=GenerationConfig.
                builder().
                responseSchema(responseSchema).
                build();
        GeminiRequest geminiRequest=GeminiRequest.
                builder().
                contents(contents).
                generationConfig(generationConfig).
                build();
        GeminiResponse geminiResponse=restClient.post()
                .uri("/models/{model}:generateContent",model)
                .header("x-goog-api-key",apikey)
                .body(geminiRequest)
                .retrieve()
                .body(GeminiResponse.class);
        if(geminiResponse.getCandidates().isEmpty())
            throw new GeminiResponseException("No candidates returned");
        if(geminiResponse.getCandidates().get(0).getContent().getParts().isEmpty())
            throw new GeminiResponseException("No parts Returned");
        String jsonText=geminiResponse.getCandidates().get(0).getContent().getParts().get(0).getText();

        try {
            return objectMapper.readValue(jsonText, GeminiRecommendationDto.class);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new GeminiResponseException("Failed to parse Gemini response");
        }
    }
}

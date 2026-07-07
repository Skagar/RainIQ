package com.rainiq.aiservice.service;

import com.rainiq.aiservice.client.GeminiClient;
import com.rainiq.aiservice.client.PropertyClient;
import com.rainiq.aiservice.client.RainfallClient;
import com.rainiq.aiservice.dto.gemini.GeminiRecommendationDto;
import com.rainiq.aiservice.entity.AiRecommendation;
import com.rainiq.aiservice.entity.AiResponseStatus;
import com.rainiq.aiservice.repository.AiRecommendationRepository;
import com.rainiq.aiservice.dto.PropertyResponseDto;
import com.rainiq.aiservice.dto.RainfallResponseDto;
import com.rainiq.aiservice.event.ComplianceCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiServiceRecommendation {
    private final PropertyClient propertyClient;
    private final RainfallClient rainfallClient;
    private final GeminiClient geminiClient;
    private final AiRecommendationRepository aiRecommendationRepository;
    public void generateRecommendation(ComplianceCompletedEvent complianceCompletedEvent) {
        try
        {
            PropertyResponseDto propertyResponseDto=propertyClient.getProperty(complianceCompletedEvent.getPropertyId());
            RainfallResponseDto rainfallResponseDto=rainfallClient.getRainfallData(propertyResponseDto.getPincode());
            String prompt="Given a rainwater harvesting design with calculated capacity "+complianceCompletedEvent.getCalculatedCapacity() +" liters, area" +complianceCompletedEvent.getRecommendedArea()+" sqm, surface type "+propertyResponseDto.getSurfaceType()
                    +", average annual rainfall "+rainfallResponseDto.getAvgRainfall()+" mm, recommend a tank size, pipe specification, filtration type, estimated installation cost in INR, and estimated annual savings in INR";
            GeminiRecommendationDto geminiRecommendationDto=geminiClient.generateRecommendation(prompt);
            AiRecommendation aiRecommendation=AiRecommendation.builder()
                    .designId(complianceCompletedEvent.getDesignId())
                    .propertyId(complianceCompletedEvent.getPropertyId())
                    .recommendedTankSizeLiters(geminiRecommendationDto.getRecommendedTankSizeLiters())
                    .recommendedFiltrationType(geminiRecommendationDto.getRecommendedFiltrationType())
                    .recommendedPipeSpec(geminiRecommendationDto.getRecommendedPipeSpec())
                    .estimatedCostInr(geminiRecommendationDto.getEstimatedCostInr())
                    .estimatedAnnualSavingsInr(geminiRecommendationDto.getEstimatedAnnualSavingsInr())
                    .status(AiResponseStatus.GENERATED)
                    .comments("Ai Recommendation generate successfully")
                    .build();
            aiRecommendationRepository.save(aiRecommendation);
        } catch (Exception e) {
            AiRecommendation aiRecommendation=AiRecommendation.builder()
                    .designId(complianceCompletedEvent.getDesignId())
                    .propertyId(complianceCompletedEvent.getPropertyId())
                    .status(AiResponseStatus.FAILED)
                    .comments("Failed due to =>"+e.getMessage())
                    .build();
            aiRecommendationRepository.save(aiRecommendation);
        }

    }
}

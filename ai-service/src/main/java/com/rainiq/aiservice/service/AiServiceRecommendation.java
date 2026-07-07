package com.rainiq.aiservice.service;

import com.rainiq.aiservice.client.PropertyClient;
import com.rainiq.aiservice.client.RainfallClient;
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
    public void generateRecommendation(ComplianceCompletedEvent complianceCompletedEvent) {
        PropertyResponseDto propertyResponseDto=propertyClient.getProperty(complianceCompletedEvent.getPropertyId());
        RainfallResponseDto rainfallResponseDto=rainfallClient.getRainfallData(propertyResponseDto.getPincode());
    }
}

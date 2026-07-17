package com.rainiq.complianceservice.client;

import com.rainiq.complianceservice.dto.PropertyClientDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class PropertyClient {
    private final RestClient restClient;

    @Value("${internal.service.token}")
    private String token;
    public PropertyClient(RestClient.Builder loadBalancedRestClientBuilder)
    {
        this.restClient=loadBalancedRestClientBuilder.baseUrl("http://property-service").build();
    }

    public PropertyClientDto getPropertyDetails(UUID propertyId)
    {
        try
        {
           return restClient.get().uri("/api/properties/{propertyId}",propertyId)
                    .header("Authorization","Bearer "+ token)
                    .retrieve()
                    .body(PropertyClientDto.class);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

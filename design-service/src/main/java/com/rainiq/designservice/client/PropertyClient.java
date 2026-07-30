package com.rainiq.designservice.client;

import com.rainiq.designservice.dto.PropertyResponseDto;
import com.rainiq.designservice.exception.InvalidRequestException;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class PropertyClient {
    private final RestClient restClient;
    public PropertyClient(RestClient.Builder loadBalancedRestClientBuilder)
    {
        this.restClient=loadBalancedRestClientBuilder.baseUrl("http://property-service").build();
    }
    public PropertyResponseDto propertyExists(UUID propertyId, String token)
    {
        try
        {
          return   restClient.get().uri("/api/properties/{id}",propertyId)
                    .header("Authorization","Bearer "+token)
                    .retrieve()
                    .body(PropertyResponseDto.class);

        } catch (Exception e) {
            throw new InvalidRequestException(e.getMessage());
        }
    }
}

package com.rainiq.aiservice.client;

import com.rainiq.aiservice.dto.PropertyResponseDto;
import com.rainiq.aiservice.exception.ServiceUnavailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class PropertyClient {

    @Value("${internal.service.token}")
    private String token;
    private final RestClient restClient;
    public PropertyClient(@Value("${property.service.url}")String baseUrl)
    {
        this.restClient=RestClient.builder().baseUrl(baseUrl).build();
    }

    public PropertyResponseDto getProperty(UUID propertyId)
    {
        try {
            return restClient.get().uri("/api/properties/{id}", propertyId)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .body(PropertyResponseDto.class);
        }
        catch (Exception e)
        {
            System.out.println("Message => "+ e.getMessage());
            throw new ServiceUnavailableException("Property service is unavailable");
        }
    }

}

package com.rainiq.notificationservice.client;

import com.rainiq.notificationservice.dto.PropertyResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class PropertyClient {

    private final RestClient restClient;

    @Value("${internal.service.token}")
    String token;
    public PropertyClient(@Value("${property.service.url}") String baseUrl)
    {
        this.restClient=RestClient.builder().baseUrl(baseUrl).build();
    }

    public PropertyResponseDto getPropertyOwnerEmail(UUID propertyId)
    {
        return restClient.get().uri("/api/properties/{propertyId}",propertyId)
                .header("Authorization","Bearer "+token)
                .retrieve()
                .body(PropertyResponseDto.class);
    }

}

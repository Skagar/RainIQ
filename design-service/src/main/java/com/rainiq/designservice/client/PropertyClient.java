package com.rainiq.designservice.client;

import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class PropertyClient {
    private final RestClient restClient;
    public PropertyClient(@Value("${property.service.url}") String baseUrl)
    {
        this.restClient=RestClient.builder().baseUrl(baseUrl).build();
    }
    public Boolean propertyExists(UUID propertyId,String token)
    {
        try
        {
            restClient.get().uri("/api/properties/{id}",propertyId)
                    .header("Authorization","Bearer "+token)
                    .retrieve()
                    .toBodilessEntity();
            return true;
        } catch (Exception e) {
            System.out.println("In property client");
            return false;
        }
    }
}

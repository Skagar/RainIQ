package com.rainiq.complianceservice.client;

import com.rainiq.complianceservice.dto.PropertyClientDto;
import com.rainiq.complianceservice.event.DesignSubmittedEvent;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class PropertyClient {
    private final RestClient restClient;

    public PropertyClient(@Value("${property.service.url}") String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    public PropertyClientDto propertyExists(UUID propertyId,String token)
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

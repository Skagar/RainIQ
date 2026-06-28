package com.rainiq.complianceservice.client;

import com.rainiq.complianceservice.dto.DesignClientDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class DesignClient {
    private final RestClient restClient;

    @Value("${internal.service.token}")
    private String token;
    public DesignClient(@Value("${design.service.url}") String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    public DesignClientDto getDesignDetails(UUID designId)
    {
        try
        {
            return restClient.get().uri("/api/designs/{designId}",designId)
                    .header("Authorization","Bearer "+ token)
                    .retrieve()
                    .body(DesignClientDto.class);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

package com.rainiq.monitoringservice.client;

import com.rainiq.monitoringservice.dto.DesignResponseDto;
import com.rainiq.monitoringservice.exception.ServiceUnavailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class DesignClient {

    private final RestClient restClient;

    @Value("${internal.service.token}")
    String token;

    public DesignClient(@Value("${design.service.url}") String baseUrl)
    {
        this.restClient=RestClient.builder().baseUrl(baseUrl).build();
    }

    public DesignResponseDto getDesign(UUID designId)
    {
        try {
            return restClient.get()
                    .uri("/api/designs/{designId}",designId)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .body(DesignResponseDto.class);
        }
        catch (Exception e)
        {
          throw new ServiceUnavailableException("Design service is currently Unavailable");
        }
    }
}

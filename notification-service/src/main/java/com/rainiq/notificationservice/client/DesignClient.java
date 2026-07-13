package com.rainiq.notificationservice.client;

import com.rainiq.notificationservice.dto.DesignResponseDto;
import com.rainiq.notificationservice.dto.PropertyResponseDto;
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

    public DesignResponseDto getDesignUserEmail(UUID designId)
    {
        return restClient.get().uri("/api/designs/{designId}",designId)
                .header("Authorization","Bearer "+token)
                .retrieve()
                .body(DesignResponseDto.class);
    }
}

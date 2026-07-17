package com.rainiq.complianceservice.client;

import com.rainiq.complianceservice.dto.RainfallClientDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class RainfallClient {
    private final RestClient restClient;

    @Value("${internal.service.token}")
    private String token;
    public RainfallClient(RestClient.Builder loadBalancedRestClientBuilder)
    {
        this.restClient=loadBalancedRestClientBuilder.baseUrl("http://rainfall-service").build();
    }

    public RainfallClientDto getRainfallDetails(String pincode)
    {
        try
        {
            return restClient.get().uri("/api/rainfalls/{pincode}",pincode)
                    .header("Authorization","Bearer "+ token)
                    .retrieve()
                    .body(RainfallClientDto.class);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

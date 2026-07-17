package com.rainiq.aiservice.client;

import com.rainiq.aiservice.dto.RainfallResponseDto;
import com.rainiq.aiservice.exception.ServiceUnavailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RainfallClient {
    @Value("${internal.service.token}")
    private String token;

    private final RestClient restClient;

    public RainfallClient(RestClient.Builder loadBalancedRestClientBuilder)
    {
        this.restClient=loadBalancedRestClientBuilder.baseUrl("http://rainfall-service").build();
    }

    public RainfallResponseDto getRainfallData(String pincode)
    {
        try {
            return restClient.get().uri("/api/rainfalls/{pincode}", pincode)
                    .header("Authorization","Bearer "+token)
                    .retrieve().body(RainfallResponseDto.class);
        }
        catch (Exception e)
        {
            System.out.println("Message => "+ e.getMessage());
            throw new ServiceUnavailableException("Rainfall Service is unavailable");
        }
    }
}

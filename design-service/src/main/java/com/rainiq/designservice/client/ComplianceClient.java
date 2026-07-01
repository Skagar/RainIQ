package com.rainiq.designservice.client;

import com.rainiq.designservice.dto.ComplianceResponseDto;
import com.rainiq.designservice.exception.ServiceUnavailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class ComplianceClient {
    @Value("${internal.service.token}")
    String token;
    private final RestClient restClient;
    public ComplianceClient(@Value("${compliance.service.url}") String baseUrl)
    {
        this.restClient=RestClient.builder().baseUrl(baseUrl).build();
    }

    public ComplianceResponseDto getCompliance (UUID designId)
    {
        try {
            return restClient.get().
                    uri("api/compliances/{designId}", designId)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .body(ComplianceResponseDto.class);
        }
        catch (Exception e)
        {
            throw new ServiceUnavailableException("Compliance Service is down try again later");
        }

    }
}

package com.rainiq.rainfallservice.client;

import com.rainiq.rainfallservice.dto.GeocodingClientResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


@Component
public class GeocodingClient {
    private final RestClient restClient;
    private final String apiKey;

    public GeocodingClient(RestClient.Builder builder, @Value("${geocoding.api.key}") String apiKey) {
        this.restClient = builder.baseUrl("https://geocode.maps.co").build();
        this.apiKey = apiKey;
    }

    public GeocodingClientResponseDto getCoordinates(String pincode)
    {
       GeocodingClientResponseDto[] geocodingClientResponseDto= restClient.get().uri("/search?postalcode={pincode}&api_key={apiKey}",pincode,apiKey).retrieve().body(GeocodingClientResponseDto[].class);
       return geocodingClientResponseDto[0];
    }
}

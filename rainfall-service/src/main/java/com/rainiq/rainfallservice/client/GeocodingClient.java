package com.rainiq.rainfallservice.client;

import com.rainiq.rainfallservice.dto.GeocodingClientResponseDto;
import com.rainiq.rainfallservice.exception.CoordinatesFetchException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
       if(geocodingClientResponseDto==null || geocodingClientResponseDto.length==0)
           throw new CoordinatesFetchException("Coordinates cannot be fetched for the given pincode");
       return geocodingClientResponseDto[0];
    }
}

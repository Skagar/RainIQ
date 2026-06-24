package com.rainiq.rainfallservice.service;

import com.rainiq.rainfallservice.client.GeocodingClient;
import com.rainiq.rainfallservice.client.RainfallClient;
import com.rainiq.rainfallservice.dto.GeocodingClientResponseDto;
import com.rainiq.rainfallservice.dto.RainfallDataResponseDto;
import com.rainiq.rainfallservice.entity.RainfallData;
import com.rainiq.rainfallservice.repository.RainfallDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RainfallService {
    private final RainfallDataRepository rainfallDataRepository;
    private final GeocodingClient geocodingClient;
    private final RainfallClient rainfallClient;

    @Value("${rainfall.stale.months}")
    private long rainfallMonths;

    public RainfallDataResponseDto getRainfallData(String pincode)
    {
        Optional<RainfallData> optionalRainfallData=rainfallDataRepository.findById(pincode);
        if(optionalRainfallData.isPresent())
        {
            RainfallData rainfallData=optionalRainfallData.get();
            if(rainfallData.getLastFetchedAt().plusMonths(rainfallMonths).isBefore(LocalDateTime.now()))
            {
                return fetchAndSave(pincode);
            }
            else
            {
              return mapToDto(rainfallData);
            }
        }
        else
        {
            return fetchAndSave(pincode);
        }
    }

    private RainfallDataResponseDto fetchAndSave(String pincode)
    {

        GeocodingClientResponseDto geocodingClientResponseDto=geocodingClient.getCoordinates(pincode);
        Double avgRainfall=rainfallClient.getAnnualRainfallData(Double.parseDouble(geocodingClientResponseDto.getLatitude()),Double.parseDouble(geocodingClientResponseDto.getLongitude()));
        RainfallData newRainfallData=RainfallData.builder()
                .avgRainfall(avgRainfall)
                .pincode(pincode)
                .latitude(Double.parseDouble(geocodingClientResponseDto.getLatitude()))
                .longitude(Double.parseDouble(geocodingClientResponseDto.getLongitude()))
                .lastFetchedAt(LocalDateTime.now()).build();
        rainfallDataRepository.save(newRainfallData);
        return mapToDto(newRainfallData);
    }
    private RainfallDataResponseDto mapToDto(RainfallData rainfallData)
    {
        RainfallDataResponseDto responseDto=RainfallDataResponseDto.builder()
                .avgRainfall(rainfallData.getAvgRainfall())
                .pincode(rainfallData.getPincode())
                .latitude(rainfallData.getLatitude())
                .longitude(rainfallData.getLongitude())
                .build();
        return responseDto;
    }
}

package com.rainiq.rainfallservice.client;

import com.rainiq.rainfallservice.dto.OpenMeteoResponseDto;
import com.rainiq.rainfallservice.exception.RainfallDataFetchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;

@Component
public class RainfallClient {
    private final RestClient restClient;

    public RainfallClient(RestClient.Builder Builder) {
        this.restClient = Builder.baseUrl("https://archive-api.open-meteo.com").build();
    }
    public Double getAnnualRainfallData(Double latitude,Double longitude)
    {
       OpenMeteoResponseDto responseDto= restClient.get().uri("/v1/archive?latitude={lat}&longitude={lon}&start_date={start}&end_date={end}&daily=precipitation_sum",latitude,longitude,"2000-01-01", LocalDate.now().minusDays(1)).retrieve().body(OpenMeteoResponseDto.class);
        if (responseDto == null || responseDto.getDailyDataDto() == null
                || responseDto.getDailyDataDto().getPrecipitationSum() == null) {
            throw new RainfallDataFetchException("Failed to fetch rainfall data");
        }
      List<Double>precipitaionSum= responseDto.getDailyDataDto().getPrecipitationSum();
      double sum=0.0;
      for (int i=0;i< precipitaionSum.size();i++)
      {
          sum+=precipitaionSum.get(i);
      }
      int years=(LocalDate.now().minusDays(1).getYear()-2000+1)*10;
      return sum/(double) years;
    }
}

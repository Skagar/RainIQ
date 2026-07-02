package com.rainiq.monitoringservice.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TankReadingResponse {
    private UUID id;
    private String deviceId;
    private UUID propertyId;
    private BigDecimal tankLevelPercent;
    private LocalDateTime recordedAt;
}

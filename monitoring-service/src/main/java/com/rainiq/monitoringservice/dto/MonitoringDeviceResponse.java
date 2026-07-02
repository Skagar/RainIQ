package com.rainiq.monitoringservice.dto;

import com.rainiq.monitoringservice.entity.MonitoringStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitoringDeviceResponse {
    private UUID id;
    private String deviceId;
    private UUID propertyId;
    private UUID designId;
    private String installedBy;
    private LocalDateTime installedAt;
    private MonitoringStatus status;
}

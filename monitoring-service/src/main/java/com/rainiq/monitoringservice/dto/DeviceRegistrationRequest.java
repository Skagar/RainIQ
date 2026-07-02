package com.rainiq.monitoringservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceRegistrationRequest {
    @NotNull(message = "Property Id cannot be empty")
    private UUID propertyId;
    @NotNull(message = "Design Id cannot be empty")
    private UUID designId;
    @NotBlank(message = "Device Id cannot be empty")
    private String deviceId;
}

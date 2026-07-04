package com.rainiq.monitoringservice.controller;

import com.rainiq.monitoringservice.dto.DeviceRegistrationRequest;
import com.rainiq.monitoringservice.dto.MonitoringDeviceResponse;
import com.rainiq.monitoringservice.service.MonitoringDeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monitoring")
public class MonitoringDeviceController {
    private final MonitoringDeviceService monitoringDeviceService;

    @PostMapping("/devices")
    @PreAuthorize("hasRole('INSPECTOR')")
    public ResponseEntity<MonitoringDeviceResponse> registerDevice(@Valid @RequestBody DeviceRegistrationRequest deviceRegistrationRequest)
    {
        return ResponseEntity.ok(monitoringDeviceService.registerDevice(deviceRegistrationRequest));
    }

    @GetMapping("/devices/{propertyId}")
    @PreAuthorize("hasRole('INSPECTOR') or hasRole('MUNICIPAL_OFFICER')")
    public ResponseEntity<MonitoringDeviceResponse> getDeviceByPropertyId(@PathVariable UUID propertyId)
    {
        return ResponseEntity.ok(monitoringDeviceService.getDeviceByPropertyId(propertyId));
    }
}

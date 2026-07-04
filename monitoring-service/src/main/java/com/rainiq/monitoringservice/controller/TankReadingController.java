package com.rainiq.monitoringservice.controller;

import com.rainiq.monitoringservice.dto.TankReadingRequest;
import com.rainiq.monitoringservice.dto.TankReadingResponse;
import com.rainiq.monitoringservice.service.TankReadingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monitoring/readings")
public class TankReadingController {

    private final TankReadingService tankReadingService;

    @PostMapping
    @PreAuthorize("hasRole('INSPECTOR')")
    public ResponseEntity<TankReadingResponse> addReading(@Valid @RequestBody TankReadingRequest tankReadingRequest)
    {
        return ResponseEntity.ok(tankReadingService.addReading(tankReadingRequest));
    }

    @GetMapping("/{propertyId}")
    @PreAuthorize("hasRole('INSPECTOR') or hasRole('MUNICIPAL_OFFICER')")
    public ResponseEntity<List<TankReadingResponse>> getReadingsByPropertyId(@PathVariable UUID propertyId)
    {
        return ResponseEntity.ok(tankReadingService.getReadingsByPropertyId(propertyId));
    }

    @GetMapping("/{propertyId}/latest")
    @PreAuthorize("hasRole('INSPECTOR') or hasRole('MUNICIPAL_OFFICER')")
    public ResponseEntity<TankReadingResponse> getLatestTankReadingByPropertyId(@PathVariable UUID propertyId)
    {
        return ResponseEntity.ok(tankReadingService.getLatestReading(propertyId));
    }
}

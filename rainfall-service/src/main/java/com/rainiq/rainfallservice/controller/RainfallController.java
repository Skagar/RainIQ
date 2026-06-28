package com.rainiq.rainfallservice.controller;

import com.rainiq.rainfallservice.dto.RainfallDataResponseDto;
import com.rainiq.rainfallservice.service.RainfallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rainfalls")
public class RainfallController {
    private final RainfallService rainfallService;

    @GetMapping("/{pincode}")
    @PreAuthorize("hasRole('MUNICIPAL_OFFICER') or hasRole('INTERNAL_SERVICE')")
    public ResponseEntity<RainfallDataResponseDto> getRainfallData(@PathVariable String pincode)
    {
        return ResponseEntity.ok(rainfallService.getRainfallData(pincode));
    }

}

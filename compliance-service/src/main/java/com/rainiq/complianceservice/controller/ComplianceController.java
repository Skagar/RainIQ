package com.rainiq.complianceservice.controller;

import com.rainiq.complianceservice.dto.ComplianceRecordDto;
import com.rainiq.complianceservice.entity.ComplianceStatus;
import com.rainiq.complianceservice.service.ComplianceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/compliances")
public class ComplianceController {
    private final ComplianceService complianceService;

    @GetMapping
    @PreAuthorize("hasRole('MUNICIPAL_OFFICER')")
    public ResponseEntity<List<ComplianceRecordDto>> getAllRecords()
    {
        return ResponseEntity.ok(complianceService.getAllComplianceRecords());
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('MUNICIPAL_OFFICER')")
    public ResponseEntity<List<ComplianceRecordDto>> getAllRecordsByStatus(@PathVariable ComplianceStatus status)
    {
        return ResponseEntity.ok(complianceService.getAllComplianceRecordsByStatus(status));
    }

    @GetMapping("/{designId}")
    @PreAuthorize("hasRole('MUNICIPAL_OFFICER')")
    public ResponseEntity<ComplianceRecordDto> getRecordByDesignId(@PathVariable UUID designId)
    {
        return ResponseEntity.ok(complianceService.getComplianceRecordByDesignId(designId));
    }

}

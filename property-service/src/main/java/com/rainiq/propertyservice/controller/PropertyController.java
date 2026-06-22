package com.rainiq.propertyservice.controller;

import com.rainiq.propertyservice.dto.PropertyRequestDto;
import com.rainiq.propertyservice.dto.PropertyResponseDto;
import com.rainiq.propertyservice.entity.PropertyStatus;
import com.rainiq.propertyservice.entity.PropertyType;
import com.rainiq.propertyservice.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<PropertyResponseDto> createProperty(@Valid @RequestBody PropertyRequestDto propertyRequestDto)
    {
        String email=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return ResponseEntity.ok(propertyService.createProperty(email,propertyRequestDto));
    }
    @GetMapping("/my")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<PropertyResponseDto>> getAllPropertiesByMail()
    {
        String email=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return ResponseEntity.ok(propertyService.getAllPropertiesByOwnerEmail(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> getPropertyById(@PathVariable UUID id)
    {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('INSPECTOR') or hasRole('MUNICIPAL_OFFICER')")
    public ResponseEntity<List<PropertyResponseDto>> getAllPropertiesByStatus(@PathVariable PropertyStatus status)
    {
        return ResponseEntity.ok(propertyService.getPropertiesByStatus(status));
    }

    @GetMapping("/type/{type}")
    @PreAuthorize("hasRole('INSPECTOR') or hasRole('MUNICIPAL_OFFICER')")
    public ResponseEntity<List<PropertyResponseDto>> getAllPropertiesByType(@PathVariable PropertyType type)
    {
        return ResponseEntity.ok(propertyService.getPropertiesByType(type));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('INSPECTOR') or hasRole('MUNICIPAL_OFFICER')")
    public ResponseEntity<List<PropertyResponseDto>> getPropertiesByStatusAndType(@RequestParam PropertyStatus propertyStatus,@RequestParam PropertyType propertyType)
    {
        return ResponseEntity.ok(propertyService.getPropertiesByStatusAndType(propertyStatus,propertyType));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<PropertyResponseDto> updateProperty(@PathVariable UUID id, @Valid @RequestBody PropertyRequestDto propertyRequestDto)
    {
        String email=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return ResponseEntity.ok(propertyService.updateProperty(id,email,propertyRequestDto));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('MUNICIPAL_OFFICER')")
    public ResponseEntity<PropertyResponseDto> updatePropertyStatus(@PathVariable UUID id, @RequestParam PropertyStatus propertyStatus)
    {
        return ResponseEntity.ok(propertyService.updatePropertyStatus(id,propertyStatus));
    }
}

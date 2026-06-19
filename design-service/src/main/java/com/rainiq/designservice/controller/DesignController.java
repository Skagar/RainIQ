package com.rainiq.designservice.controller;

import com.rainiq.designservice.dto.DesignRequest;
import com.rainiq.designservice.dto.DesignResponse;
import com.rainiq.designservice.service.DesignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/designs")
public class DesignController {

    private final DesignService designService;

    @PostMapping
    @PreAuthorize("hasRole('OWNER') or hasRole('ARCHITECT')")
    public ResponseEntity<DesignResponse> submitDesign(@RequestBody @Valid DesignRequest designRequest)
    {
        String email=(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(designService.submitDesign(designRequest,email));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('OWNER') or hasRole('ARCHITECT')")
    public  ResponseEntity <List<DesignResponse>> getMyDesigns()
    {
        String email=(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(designService.getMyDesigns(email));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('ARCHITECT')")
    public ResponseEntity<DesignResponse> updateDesign(@RequestBody @Valid DesignRequest designRequest, @PathVariable UUID id)
    {
        String email=(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(designService.updateDesign(designRequest,email,id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('ARCHITECT')")
    public ResponseEntity<Void> deleteDesign(@PathVariable UUID id)
    {
        String email=(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        designService.deleteDesign(id,email);
        return ResponseEntity.noContent().build();
    }
}

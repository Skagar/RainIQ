package com.rainiq.designservice.controller;

import com.rainiq.designservice.dto.ReviewRequest;
import com.rainiq.designservice.dto.ReviewResponse;
import com.rainiq.designservice.service.ReviewService;
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
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/pending")
    @PreAuthorize("hasRole('INSPECTOR') or hasRole('MUNICIPAL_OFFICER')")
    public ResponseEntity<List<ReviewResponse>> getAllPendingReviews()
    {
        return ResponseEntity.ok(reviewService.getAllPendingReviews());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('INSPECTOR') or hasRole('MUNICIPAL_OFFICER')")
    public ResponseEntity<ReviewResponse> updateReviewByDesignId(@PathVariable UUID id, @RequestBody @Valid ReviewRequest reviewRequest)
    {
        String email= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(reviewService.updateReviewByDesignId(id,reviewRequest,email));
    }
}

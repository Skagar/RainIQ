package com.rainiq.designservice.dto;

import com.rainiq.designservice.entity.ReviewStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewResponse {
    private UUID propertyId;
    private UUID designId;
    private UUID reviewId;
    private String userEmail;
    private String officerEmail;
    private BigDecimal area;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ReviewStatus status;
    private LocalDateTime reviewedAt;
    private String comments;
}

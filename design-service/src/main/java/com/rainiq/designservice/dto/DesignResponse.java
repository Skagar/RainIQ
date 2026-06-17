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
public class DesignResponse {
    private UUID designId;
    private String userEmail;
    private String location;
    private BigDecimal area;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String comments;
    private LocalDateTime reviewedAt;
    private ReviewStatus status;
}

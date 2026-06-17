package com.rainiq.designservice.dto;

import com.rainiq.designservice.entity.ReviewStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewRequest {
    @NotNull(message = "Status can't be empty")
    private ReviewStatus status;
    private String comment;
}

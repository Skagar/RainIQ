package com.rainiq.rainfallservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "rainfall_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RainfallData {

    @Id
    @Column
    private String pincode;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column(name = "avg_rainfall")
    private Double avgRainfall;

    @Column(name = "last_fetched_at")
    private LocalDateTime lastFetchedAt;
}

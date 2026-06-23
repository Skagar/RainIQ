package com.rainiq.rainfallservice.repository;

import com.rainiq.rainfallservice.entity.RainfallData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RainfallDataRepository extends JpaRepository<RainfallData,String> {
}

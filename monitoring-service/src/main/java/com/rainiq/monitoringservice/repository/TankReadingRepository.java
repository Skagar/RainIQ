package com.rainiq.monitoringservice.repository;

import com.rainiq.monitoringservice.entity.TankReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TankReadingRepository extends JpaRepository<TankReading, UUID> {
    List<TankReading> findByPropertyId(UUID propertyId);

    Optional<TankReading> findTopByPropertyIdOrderByRecordedAtDesc(UUID propertyId);
    Optional<TankReading> findByDeviceId(String deviceId);
}

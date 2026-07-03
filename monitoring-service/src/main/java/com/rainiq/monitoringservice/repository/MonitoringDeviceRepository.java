package com.rainiq.monitoringservice.repository;

import com.rainiq.monitoringservice.entity.MonitoringDevice;
import com.rainiq.monitoringservice.entity.MonitoringStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MonitoringDeviceRepository extends JpaRepository<MonitoringDevice,UUID> {
    Optional<MonitoringDevice> findByPropertyId(UUID propertyId);
    Optional<MonitoringDevice> findByDeviceId(String deviceId);
}

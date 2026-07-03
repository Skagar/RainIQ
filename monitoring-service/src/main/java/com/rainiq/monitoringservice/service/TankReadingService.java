package com.rainiq.monitoringservice.service;

import com.rainiq.monitoringservice.dto.TankReadingRequest;
import com.rainiq.monitoringservice.dto.TankReadingResponse;
import com.rainiq.monitoringservice.entity.MonitoringDevice;
import com.rainiq.monitoringservice.entity.MonitoringStatus;
import com.rainiq.monitoringservice.entity.TankReading;
import com.rainiq.monitoringservice.exception.InvalidRequestException;
import com.rainiq.monitoringservice.exception.ResourceNotFoundException;
import com.rainiq.monitoringservice.repository.MonitoringDeviceRepository;
import com.rainiq.monitoringservice.repository.TankReadingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TankReadingService {
    private final TankReadingRepository tankReadingRepository;
    private final MonitoringDeviceRepository monitoringDeviceRepository;

    public TankReadingResponse addReading(TankReadingRequest tankReadingRequest)
    {
        Optional<MonitoringDevice> optionalMonitoringDevice=monitoringDeviceRepository.findByPropertyId(tankReadingRequest.getPropertyId());
        if(optionalMonitoringDevice.isEmpty())
            throw new ResourceNotFoundException("No  device registered with the given property id");
        if(optionalMonitoringDevice.get().getStatus().equals(MonitoringStatus.INACTIVE))
            throw new InvalidRequestException("Device registered with the given property id is inactive");
        if(!optionalMonitoringDevice.get().getDeviceId().equals(tankReadingRequest.getDeviceId()))
            throw new InvalidRequestException("Device id does not match the registered device for this property");
        BigDecimal tankLevel=tankReadingRequest.getTankLevelPercent();
        if(tankLevel.compareTo(BigDecimal.ZERO) < 0 || tankLevel.compareTo(BigDecimal.valueOf(100)) > 0)
            throw new InvalidRequestException("Tank level percent can't be negative or greater than 100");
        TankReading tankReading=TankReading
                .builder()
                .deviceId(tankReadingRequest.getDeviceId())
                .propertyId(tankReadingRequest.getPropertyId())
                .tankLevelPercent(tankReadingRequest.getTankLevelPercent())
                .build();
        tankReadingRepository.save(tankReading);
        return mapToDto(tankReading);
    }

    public List<TankReadingResponse> getReadingsByPropertyId(UUID propertyId)
    {
        List<TankReading> tankReadingList=tankReadingRepository.findByPropertyIdOrderByRecordedAtDesc(propertyId);
        return tankReadingList.stream().map(this::mapToDto).toList();
    }

    public TankReadingResponse getLatestReading(UUID propertyId)
    {
        Optional<TankReading> optionalTankReading=tankReadingRepository.findTopByPropertyIdOrderByRecordedAtDesc(propertyId);
        if(optionalTankReading.isEmpty())
            throw new ResourceNotFoundException("No readings found with the given property id");
        return mapToDto(optionalTankReading.get());
    }

    private TankReadingResponse mapToDto(TankReading tankReading)
    {
        TankReadingResponse tankReadingResponse=TankReadingResponse
                .builder()
                .id(tankReading.getId())
                .deviceId(tankReading.getDeviceId())
                .propertyId(tankReading.getPropertyId())
                .tankLevelPercent(tankReading.getTankLevelPercent())
                .recordedAt(tankReading.getRecordedAt()).build();
        return tankReadingResponse;
    }
}

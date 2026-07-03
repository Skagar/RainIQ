package com.rainiq.monitoringservice.service;

import com.rainiq.monitoringservice.client.DesignClient;
import com.rainiq.monitoringservice.dto.DesignResponseDto;
import com.rainiq.monitoringservice.dto.DeviceRegistrationRequest;
import com.rainiq.monitoringservice.dto.MonitoringDeviceResponse;
import com.rainiq.monitoringservice.entity.MonitoringDevice;
import com.rainiq.monitoringservice.entity.MonitoringStatus;
import com.rainiq.monitoringservice.exception.InvalidRequestException;
import com.rainiq.monitoringservice.exception.ResourceNotFoundException;
import com.rainiq.monitoringservice.repository.MonitoringDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MonitoringDeviceService {

    private final DesignClient designClient;
    private final MonitoringDeviceRepository monitoringDeviceRepository;

    public MonitoringDeviceResponse registerDevice(DeviceRegistrationRequest deviceRegistrationRequest)
    {
        DesignResponseDto designResponseDto=designClient.getDesign(deviceRegistrationRequest.getDesignId());
        if(!designResponseDto.getStatus().equalsIgnoreCase("Approved"))
            throw new InvalidRequestException("Design is not yet Approved");
        if(!designResponseDto.getPropertyId().equals(deviceRegistrationRequest.getPropertyId()))
            throw new InvalidRequestException("Property ids mismatched");
        Optional<MonitoringDevice> optionalMonitoringDevice=monitoringDeviceRepository.findByDeviceId(deviceRegistrationRequest.getDeviceId());
        Optional<MonitoringDevice> monitoringDeviceOptional=monitoringDeviceRepository.findByPropertyId(deviceRegistrationRequest.getPropertyId());
        if(monitoringDeviceOptional.isPresent())
            throw new InvalidRequestException("Property has already a device registered with it");
        if(optionalMonitoringDevice.isPresent())
            throw new InvalidRequestException("Device is already registered with given property id");
        String email= SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        MonitoringDevice monitoringDevice=MonitoringDevice.builder()
                .deviceId(deviceRegistrationRequest.getDeviceId())
                .designId(deviceRegistrationRequest.getDesignId())
                .propertyId(deviceRegistrationRequest.getPropertyId())
                .status(MonitoringStatus.ACTIVE)
                .installedBy(email)
                .build();
        monitoringDeviceRepository.save(monitoringDevice);
        return mapToDto(monitoringDevice);
    }

    public MonitoringDeviceResponse getDeviceByPropertyId(UUID propertyId)
    {
        Optional<MonitoringDevice> optionalMonitoringDevice=monitoringDeviceRepository.findByPropertyId(propertyId);
        if(!optionalMonitoringDevice.isPresent())
        throw new ResourceNotFoundException("No device found registered with given property id");
        MonitoringDevice monitoringDevice=optionalMonitoringDevice.get();
        return mapToDto(monitoringDevice);
    }

    private MonitoringDeviceResponse mapToDto(MonitoringDevice monitoringDevice)
    {
        MonitoringDeviceResponse monitoringDeviceResponse=MonitoringDeviceResponse
                .builder()
                .id(monitoringDevice.getId())
                .deviceId(monitoringDevice.getDeviceId())
                .propertyId(monitoringDevice.getPropertyId())
                .designId(monitoringDevice.getDesignId())
                .status(monitoringDevice.getStatus())
                .installedBy(monitoringDevice.getInstalledBy())
                .installedAt(monitoringDevice.getInstalledAt())
                .build();
        return monitoringDeviceResponse;
    }
}

package com.rainiq.complianceservice.service;

import com.rainiq.complianceservice.Repository.ComplianceRepository;
import com.rainiq.complianceservice.client.DesignClient;
import com.rainiq.complianceservice.client.PropertyClient;
import com.rainiq.complianceservice.client.RainfallClient;
import com.rainiq.complianceservice.dto.DesignClientDto;
import com.rainiq.complianceservice.dto.PropertyClientDto;
import com.rainiq.complianceservice.dto.RainfallClientDto;
import com.rainiq.complianceservice.entity.ComplianceRecord;
import com.rainiq.complianceservice.entity.ComplianceStatus;
import com.rainiq.complianceservice.event.DesignSubmittedEvent;
import com.rainiq.complianceservice.exception.InsufficientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplianceService {
    private final ComplianceRepository complianceRepository;
    private final DesignClient designClient;
    private final PropertyClient propertyClient;
    private final RainfallClient rainfallClient;
    public void processCompliance(DesignSubmittedEvent designSubmittedEvent)
    {
        UUID designId=designSubmittedEvent.getDesignId();
        UUID propertyId=designSubmittedEvent.getPropertyId();
        Optional<ComplianceRecord> optionalComplianceRecord=complianceRepository.findByDesignId(designId);
        if(optionalComplianceRecord.isPresent())
        {
             return;
        }
        DesignClientDto designClientDto=new DesignClientDto();
        PropertyClientDto propertyClientDto=new PropertyClientDto();
        RainfallClientDto rainfallClientDto=new RainfallClientDto();
        designClientDto= designClient.getDesignDetails(designId);
        propertyClientDto=propertyClient.getPropertyDetails(propertyId);
        rainfallClientDto=rainfallClient.getRainfallDetails(propertyClientDto.getPincode());
        double avgRainfall= (rainfallClientDto.getAvgRainfall())*10;
        double avgRainfallInMetres = avgRainfall / 1000.0;
        BigDecimal designArea=designClientDto.getDesignArea();
        BigDecimal propertyArea=propertyClientDto.getPropertyArea();
        if(avgRainfall > 1200.0)
        {
            BigDecimal cap=BigDecimal.ZERO;
            if(designArea.compareTo(BigDecimal.valueOf(0.2).multiply(propertyArea))>=0)
            {

                if(propertyClientDto.getSurfaceType().equalsIgnoreCase("RCC"))
                {
                    cap=calculateCapacity(designClientDto.getDesignArea(),avgRainfallInMetres,0.85);
                } else if (propertyClientDto.getSurfaceType().equalsIgnoreCase("TILED")) {
                    cap=calculateCapacity(designClientDto.getDesignArea(),avgRainfallInMetres,0.75);
                }
                else if (propertyClientDto.getSurfaceType().equalsIgnoreCase("PAVED")) {
                    cap=calculateCapacity(designClientDto.getDesignArea(),avgRainfallInMetres,0.60);
                }
                ComplianceRecord complianceRecord=ComplianceRecord.builder()
                        .designId(designId)
                        .propertyId(propertyId)
                        .calculatedCapacity(cap)
                        .complianceStatus(ComplianceStatus.PASSED)
                        .recommendedArea(BigDecimal.valueOf(0.2).multiply(propertyArea)).build();
                complianceRepository.save(complianceRecord);
            }
            else
            {
                ComplianceRecord complianceRecord=ComplianceRecord.builder()
                        .designId(designId)
                        .propertyId(propertyId)
                        .calculatedCapacity(cap)
                        .complianceStatus(ComplianceStatus.FAILED)
                        .reason("Design area insufficient for the given rainfall zone")
                        .recommendedArea(BigDecimal.valueOf(0.2).multiply(propertyArea)).build();
                complianceRepository.save(complianceRecord);
            }

        }
        else if(avgRainfall<= 1200.0 && avgRainfall>=600.0 )
        {
            BigDecimal cap=BigDecimal.ZERO;
            if(designArea.compareTo(BigDecimal.valueOf(0.3).multiply(propertyArea))>=0)
            {
                   if(propertyClientDto.getSurfaceType().equalsIgnoreCase("RCC"))
                   {
                       cap=calculateCapacity(designClientDto.getDesignArea(),avgRainfallInMetres,0.85);
                   } else if (propertyClientDto.getSurfaceType().equalsIgnoreCase("TILED")) {
                       cap=calculateCapacity(designClientDto.getDesignArea(),avgRainfallInMetres,0.75);
                   }
                   else if (propertyClientDto.getSurfaceType().equalsIgnoreCase("PAVED")) {
                       cap=calculateCapacity(designClientDto.getDesignArea(),avgRainfallInMetres,0.60);
                   }
                   ComplianceRecord complianceRecord=ComplianceRecord.builder()
                           .designId(designId)
                           .propertyId(propertyId)
                           .calculatedCapacity(cap)
                           .complianceStatus(ComplianceStatus.PASSED)
                           .recommendedArea(BigDecimal.valueOf(0.3).multiply(propertyArea)).build();
                complianceRepository.save(complianceRecord);
            }
            else
            {
                ComplianceRecord complianceRecord=ComplianceRecord.builder()
                        .designId(designId)
                        .propertyId(propertyId)
                        .calculatedCapacity(cap)
                        .complianceStatus(ComplianceStatus.FAILED)
                        .reason("Design area insufficient for the given rainfall zone")
                        .recommendedArea(BigDecimal.valueOf(0.3).multiply(propertyArea)).build();
                complianceRepository.save(complianceRecord);
            }
        }
        else if (avgRainfall >0.0 && avgRainfall<600.0)
        {
            BigDecimal cap=BigDecimal.ZERO;

            if(designArea.compareTo(BigDecimal.valueOf(0.4).multiply(propertyArea))>=0)
            {
                if(propertyClientDto.getSurfaceType().equalsIgnoreCase("RCC"))
                {
                    cap=calculateCapacity(designClientDto.getDesignArea(),avgRainfallInMetres,0.85);
                } else if (propertyClientDto.getSurfaceType().equalsIgnoreCase("TILED")) {
                    cap=calculateCapacity(designClientDto.getDesignArea(),avgRainfallInMetres,0.75);
                }
                else if (propertyClientDto.getSurfaceType().equalsIgnoreCase("PAVED")) {
                    cap=calculateCapacity(designClientDto.getDesignArea(),avgRainfallInMetres,0.60);
                }
                ComplianceRecord complianceRecord=ComplianceRecord.builder()
                        .designId(designId)
                        .propertyId(propertyId)
                        .calculatedCapacity(cap)
                        .complianceStatus(ComplianceStatus.PASSED)
                        .recommendedArea(BigDecimal.valueOf(0.4).multiply(propertyArea)).build();
                complianceRepository.save(complianceRecord);
            }
            else
            {
                ComplianceRecord complianceRecord=ComplianceRecord.builder()
                        .designId(designId)
                        .propertyId(propertyId)
                        .calculatedCapacity(cap)
                        .complianceStatus(ComplianceStatus.FAILED)
                        .reason("Design area insufficient for the given rainfall zone")
                        .recommendedArea(BigDecimal.valueOf(0.4).multiply(propertyArea)).build();
                complianceRepository.save(complianceRecord);
            }
        }
        else
            throw new InsufficientException("Invalid rainfall data obtained");

    }

    private BigDecimal calculateCapacity(BigDecimal designArea, Double annualRainfall,Double cof)
    {
        return designArea.multiply(BigDecimal.valueOf(annualRainfall)).multiply(BigDecimal.valueOf(cof));
    }

    }



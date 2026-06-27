package com.rainiq.propertyservice.service;

import com.rainiq.propertyservice.dto.PropertyRequestDto;
import com.rainiq.propertyservice.dto.PropertyResponseDto;
import com.rainiq.propertyservice.entity.Property;
import com.rainiq.propertyservice.entity.PropertyStatus;
import com.rainiq.propertyservice.entity.PropertyType;
import com.rainiq.propertyservice.exception.InvalidRequestException;
import com.rainiq.propertyservice.exception.ResourceNotFoundException;
import com.rainiq.propertyservice.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;

    public PropertyResponseDto createProperty(String email, PropertyRequestDto propertyRequestDto)
    {
        Optional<Property> optionalProperty=propertyRepository.findByRegistrationNumber(propertyRequestDto.getRegistrationNumber());
        if(optionalProperty.isPresent())
            throw new InvalidRequestException("Property already exits with the same Registration Number");
        else
        {
            Property property= Property.builder().
                    ownerEmail(email)
                    .address(propertyRequestDto.getAddress())
                    .registrationNumber(propertyRequestDto.getRegistrationNumber())
                    .city(propertyRequestDto.getCity())
                    .state(propertyRequestDto.getState())
                    .pincode(propertyRequestDto.getPincode())
                    .area(propertyRequestDto.getArea())
                    .propertyType(propertyRequestDto.getPropertyType())
                    .surfaceType(propertyRequestDto.getSurfaceType())
                    .status(PropertyStatus.UNDER_REVIEW)
                    .build();
            propertyRepository.save(property);
            return mapToDto(property);
        }
    }

    public List<PropertyResponseDto>  getAllPropertiesByOwnerEmail(String email)
    {
        List<Property> propertyList=propertyRepository.findByOwnerEmail(email);
        return propertyList.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public PropertyResponseDto getPropertyById(UUID id)
    {
        Property property=propertyRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No property found with the given id"));
        return mapToDto(property);
    }

    public List<PropertyResponseDto> getPropertiesByStatus(PropertyStatus propertyStatus)
    {
        List<Property> propertyList=propertyRepository.findByStatus(propertyStatus);
        return propertyList.stream().map(this::mapToDto).collect(Collectors.toList());
    }
    public List<PropertyResponseDto> getPropertiesByType(PropertyType propertyType)
    {
        List<Property> propertyList=propertyRepository.findByPropertyType(propertyType);
        return propertyList.stream().map(this::mapToDto).collect(Collectors.toList());
    }
    public List<PropertyResponseDto> getPropertiesByStatusAndType(PropertyStatus propertyStatus,PropertyType propertyType)
    {
        List<Property> propertyList=propertyRepository.findByPropertyTypeAndStatus(propertyType,propertyStatus);
        return propertyList.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public PropertyResponseDto updateProperty(UUID id,String email,PropertyRequestDto propertyRequestDto)
    {
        Optional<Property>propertyOptional=propertyRepository.findByIdAndOwnerEmail(id,email);
        if(propertyOptional.isPresent())
        {
            Property property=propertyOptional.get();
            if(property.getStatus()==PropertyStatus.REJECTED)
            {
                property.setAddress(propertyRequestDto.getAddress());
                property.setCity(propertyRequestDto.getCity());
                property.setState(propertyRequestDto.getState());
                property.setPincode(propertyRequestDto.getPincode());
                property.setArea(propertyRequestDto.getArea());
                property.setPropertyType(propertyRequestDto.getPropertyType());
                property.setStatus(PropertyStatus.UNDER_REVIEW);
                property.setSurfaceType(propertyRequestDto.getSurfaceType());
                propertyRepository.save(property);
                return mapToDto(property);
            }
            else
                throw new InvalidRequestException("Property can't be updated currently property is "+property.getStatus());
        }
        else
            throw new ResourceNotFoundException("No property associated with given id and mail");
    }

    public PropertyResponseDto updatePropertyStatus(UUID id,PropertyStatus propertyStatus)
    {
        Optional<Property> optionalProperty=propertyRepository.findById(id);
        if(optionalProperty.isPresent())
        {
            Property property=optionalProperty.get();
            property.setStatus(propertyStatus);
            propertyRepository.save(property);
            return mapToDto(property);
        }
        else
            throw new ResourceNotFoundException("No property found with given id");
    }
    private PropertyResponseDto mapToDto(Property property)
    {
        PropertyResponseDto propertyResponseDto=new PropertyResponseDto();
        propertyResponseDto.setId(property.getId());
        propertyResponseDto.setOwnerEmail(property.getOwnerEmail());
        propertyResponseDto.setAddress(property.getAddress());
        propertyResponseDto.setCity(property.getCity());
        propertyResponseDto.setState(property.getState());
        propertyResponseDto.setPincode(property.getPincode());
        propertyResponseDto.setArea(property.getArea());
        propertyResponseDto.setPropertyType(property.getPropertyType());
        propertyResponseDto.setStatus(property.getStatus());
        propertyResponseDto.setCreatedAt(property.getCreatedAt());
        propertyResponseDto.setUpdatedAt(property.getUpdatedAt());
        propertyResponseDto.setSurfaceType(property.getSurfaceType());
        return propertyResponseDto;
    }
}

package com.rainiq.propertyservice.repository;

import com.rainiq.propertyservice.entity.Property;
import com.rainiq.propertyservice.entity.PropertyStatus;
import com.rainiq.propertyservice.entity.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID> {
    Optional<Property> findByOwnerEmailAndAddressAndCityAndPincode(String ownerEmail, String address, String city, String pincode);
    Optional<Property> findByIdAndOwnerEmail(UUID id, String ownerEmail);
    List<Property> findByOwnerEmail(String ownerEmail);
    List<Property> findByPropertyTypeAndStatus(PropertyType propertyType, PropertyStatus status);
    List<Property> findByStatus(PropertyStatus status);
    List<Property> findByPropertyType(PropertyType propertyType);
}

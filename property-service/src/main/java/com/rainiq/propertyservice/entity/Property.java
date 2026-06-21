package com.rainiq.propertyservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "properties")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String ownerEmail;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String pincode;

    @Column(nullable = false)
    private BigDecimal area;

    @Column(nullable = false, name = "property_type")
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PropertyStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private  LocalDateTime updatedAt;

    @PrePersist
    private void created()
    {
        createdAt=LocalDateTime.now();
    }

    @PreUpdate
    private  void  updated()
    {
        updatedAt=LocalDateTime.now();
    }

}

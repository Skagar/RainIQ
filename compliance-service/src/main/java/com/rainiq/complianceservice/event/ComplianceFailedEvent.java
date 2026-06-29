package com.rainiq.complianceservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplianceFailedEvent {
    private UUID designId;
    private UUID propertyId;
    private  String reason;
}

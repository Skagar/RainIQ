package com.rainiq.notificationservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplianceFailedEvent {
    private UUID designId;
    private UUID propertyId;
    private  String reason;
}

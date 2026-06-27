package com.rainiq.designservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesignSubmittedEvent {

    private UUID designId;
    private UUID propertyId;
}

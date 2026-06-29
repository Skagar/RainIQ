package com.rainiq.complianceservice.consumer;

import com.rainiq.complianceservice.event.DesignSubmittedEvent;
import com.rainiq.complianceservice.service.ComplianceService;
import com.rainiq.complianceservice.topics.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DesignEventConsumer {
  private final ComplianceService complianceService;
    @KafkaListener(topics = KafkaTopics.DESIGN_SUBMITTED)
    public void handleDesignSubmitted(DesignSubmittedEvent designSubmittedEvent)
    {
     complianceService.processCompliance(designSubmittedEvent);
    }
}

package com.rainiq.complianceservice.consumer;

import com.rainiq.complianceservice.event.DesignSubmittedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DesignEventConsumer {

    @KafkaListener(topics = "design.submitted")
    public void handleDesignSubmitted(DesignSubmittedEvent designSubmittedEvent)
    {

    }
}

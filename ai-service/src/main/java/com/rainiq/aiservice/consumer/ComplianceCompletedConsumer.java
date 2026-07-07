package com.rainiq.aiservice.consumer;

import com.rainiq.aiservice.event.ComplianceCompletedEvent;
import com.rainiq.aiservice.service.AiServiceRecommendation;
import com.rainiq.aiservice.topics.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ComplianceCompletedConsumer {
      private final AiServiceRecommendation aiServiceRecommendation;
    @KafkaListener(topics = KafkaTopics.COMPLIANCE_COMPLETED)
    public void handleComplianceCompleted(ComplianceCompletedEvent  complianceCompletedEvent)
    {
     aiServiceRecommendation.generateRecommendation(complianceCompletedEvent);
    }
}

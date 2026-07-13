package com.rainiq.notificationservice.consumer;

import com.rainiq.notificationservice.event.AiCompletedEvent;
import com.rainiq.notificationservice.event.ComplianceFailedEvent;
import com.rainiq.notificationservice.service.NotificationService;
import com.rainiq.notificationservice.topics.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = KafkaTopics.AI_COMPLETED,containerFactory = "aiCompletedEventConcurrentKafkaListenerContainerFactory")
    public void handleAiCompletedEvent(AiCompletedEvent event)
    {
        notificationService.generateAiCompletedEventNotification(event);
    }
    @KafkaListener(
            topics = KafkaTopics.COMPLIANCE_FAILED,
            containerFactory = "complianceFailedEventConcurrentKafkaListenerContainerFactory"
    )
    public void consumeComplianceFailedEvent(ComplianceFailedEvent event) {
        notificationService.generateComplianceFailedNotification(event);
    }
}

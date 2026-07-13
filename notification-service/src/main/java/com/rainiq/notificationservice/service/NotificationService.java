package com.rainiq.notificationservice.service;

import com.rainiq.notificationservice.event.AiCompletedEvent;
import com.rainiq.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void generateAiCompletedEventNotification(AiCompletedEvent aiCompletedEvent)
    {

    }
}

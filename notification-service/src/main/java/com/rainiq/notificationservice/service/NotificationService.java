package com.rainiq.notificationservice.service;

import com.rainiq.notificationservice.client.DesignClient;
import com.rainiq.notificationservice.client.PropertyClient;
import com.rainiq.notificationservice.entity.EventType;
import com.rainiq.notificationservice.entity.Notification;
import com.rainiq.notificationservice.entity.NotificationStatus;
import com.rainiq.notificationservice.event.AiCompletedEvent;
import com.rainiq.notificationservice.event.ComplianceFailedEvent;
import com.rainiq.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final DesignClient designClient;
    private final PropertyClient propertyClient;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;
    public void createAiCompletedNotification(String receiverEmail, AiCompletedEvent event)
    {
        String mailBody = """
Dear User,

Your AI-generated rainwater harvesting recommendation has been completed successfully.

Recommendation Details:
Recommendation ID: %s
Design ID: %s
Property ID: %s

Recommended Configuration:
Recommended Tank Size: %s liters
Recommended Pipe Specification: %s
Recommended Filtration Type: %s

Cost & Savings Estimate:
Estimated Installation Cost: ₹%s
Estimated Annual Savings: ₹%s

Thank you for using our AI-powered recommendation service.

Regards,
RainIQ Team""".formatted(
                event.getRecommendationId(),
                event.getDesignId(),
                event.getPropertyId(),
                event.getRecommendedTankSizeLiters(),
                event.getRecommendedPipeSpec(),
                event.getRecommendedFiltrationType(),
                event.getEstimatedCostInr(),
                event.getEstimatedAnnualSavingsInr()
        );
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(senderEmail);
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("Suggested design specifications for your submitted design corresponding to design id " + event.getDesignId());
        mailMessage.setText(mailBody);
        javaMailSender.send(mailMessage);
    }
    private void sendAndRecord(String recipientEmail, AiCompletedEvent event, EventType eventType) {
        try {
            createAiCompletedNotification(recipientEmail, event);
            Notification notification = mapToEntity(event, eventType, recipientEmail, NotificationStatus.SENT);
            notificationRepository.save(notification);
        } catch (Exception e) {
            Notification notification = mapToEntity(event, eventType, recipientEmail, NotificationStatus.FAILED);
            notification.setFailureReason("Unable to send email: " + e.getMessage());
            notificationRepository.save(notification);
        }
    }
    public void generateAiCompletedEventNotification(AiCompletedEvent event) {
        String ownerEmail, userEmail;
        try {
            ownerEmail = propertyClient.getPropertyOwnerEmail(event.getPropertyId()).getOwnerEmail();
            userEmail = designClient.getDesignUserEmail(event.getDesignId()).getUserEmail();
        } catch (Exception e) {
            Notification notification = mapToEntity(event, EventType.AI_RECOMMENDATION_READY, null, NotificationStatus.FAILED);
            notification.setFailureReason("Unable to fetch recipient email: " + e.getMessage());
            notificationRepository.save(notification);
            return;
        }

        if (ownerEmail.equalsIgnoreCase(userEmail)) {
            sendAndRecord(ownerEmail, event, EventType.AI_RECOMMENDATION_READY);
        } else {
            sendAndRecord(ownerEmail, event, EventType.AI_RECOMMENDATION_READY);
            sendAndRecord(userEmail, event, EventType.AI_RECOMMENDATION_READY);
        }
    }

    public Notification mapToEntity(AiCompletedEvent aiCompletedEvent, EventType eventType, String email, NotificationStatus status)
    {
        Notification notification=Notification.builder()
                .designId(aiCompletedEvent.getDesignId())
                .propertyId(aiCompletedEvent.getPropertyId())
                .recommendationId(aiCompletedEvent.getRecommendationId())
                .eventType(eventType)
                .recipientEmail(email)
                .status(status)
                .build();
        return notification;
    }



    public void createComplianceFailedNotification(String receiverEmail, ComplianceFailedEvent event)
    {
        String mailBody = """
Dear User,

We regret to inform you that your compliance verification has failed.

Details:
Design ID: %s
Property ID: %s
Reason: %s

Please review the above reason, make the necessary changes, and resubmit your design for compliance verification.

If you have any questions or need assistance, feel free to contact our support team.

Regards,
RainIQ Team""".formatted(
                event.getDesignId(),
                event.getPropertyId(),
                event.getReason()
        );
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setFrom(senderEmail);
        mailMessage.setSubject("Compliance verification failed for your design");
        mailMessage.setText(mailBody);
        javaMailSender.send(mailMessage);
    }
    private void sendAndRecord(String recipientEmail, ComplianceFailedEvent event, EventType eventType) {
        try {
            createComplianceFailedNotification(recipientEmail, event);
            Notification notification = mapToEntity(event, eventType, recipientEmail, NotificationStatus.SENT);
            notificationRepository.save(notification);
        } catch (Exception e) {
            Notification notification = mapToEntity(event, eventType, recipientEmail, NotificationStatus.FAILED);
            notification.setFailureReason("Unable to send email: " + e.getMessage());
            notificationRepository.save(notification);
        }
    }

    public void generateComplianceFailedNotification(ComplianceFailedEvent event)
    {
        String ownerEmail, userEmail;
        try {
            ownerEmail = propertyClient.getPropertyOwnerEmail(event.getPropertyId()).getOwnerEmail();
            userEmail = designClient.getDesignUserEmail(event.getDesignId()).getUserEmail();
        } catch (Exception e) {
            Notification notification = mapToEntity(event, EventType.COMPLIANCE_FAILED, null, NotificationStatus.FAILED);
            notification.setFailureReason("Unable to fetch recipient email: " + e.getMessage());
            notificationRepository.save(notification);
            return;
        }

        if (ownerEmail.equalsIgnoreCase(userEmail)) {
            sendAndRecord(ownerEmail, event, EventType.COMPLIANCE_FAILED);
        } else {
            sendAndRecord(ownerEmail, event, EventType.COMPLIANCE_FAILED);
            sendAndRecord(userEmail, event, EventType.COMPLIANCE_FAILED);
        }
    }

    public Notification mapToEntity(ComplianceFailedEvent complianceFailedEvent, EventType eventType, String email, NotificationStatus status)
    {
       Notification notification=Notification.builder()
               .designId(complianceFailedEvent.getDesignId())
               .propertyId(complianceFailedEvent.getPropertyId())
               .recipientEmail(email)
               .eventType(eventType)
               .status(status)
               .build();
       return  notification;
    }
}

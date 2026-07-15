package com.notifyhub.email_service.consumer;
import com.notifyhub.common.dto.NotificationEventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.notifyhub.email_service.service.EmailService;
import com.notifyhub.email_service.service.IdempotencyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import com.notifyhub.common.enums.NotificationType;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailConsumer {
    private final EmailService emailService;
    private final ObjectMapper objectMapper;
    private final IdempotencyService idempotencyService;

    @KafkaListener(topics = "${app.kafka.notification-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String payload) {
        NotificationEventPayload event;
        try {
            event = objectMapper.readValue(payload, NotificationEventPayload.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize notification event", e);
        }
        if(idempotencyService.isProcessed(event.getNotificationId())){
            log.info("Notification {} has already been processed.", event.getNotificationId());
            return;
        }
        if(event.getType() != NotificationType.EMAIL) {
            return;
        }
        if ("FAIL".equalsIgnoreCase(event.getTitle())) {
            throw new RuntimeException("Simulating email service failure");
        }
        emailService.process(event);
        idempotencyService.markAsProcessed(event.getNotificationId());
    }
    

}

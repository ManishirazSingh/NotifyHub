package com.notifyhub.sms_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notifyhub.common.dto.NotificationEventPayload;
import com.notifyhub.common.enums.NotificationType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.notifyhub.sms_service.service.IdempotencyService;
import com.notifyhub.sms_service.service.SmsService;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsConsumer {
    private final SmsService smsService;
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
        if(event.getType() != NotificationType.SMS) {
            return;
        }
        if(idempotencyService.isProcessed(event.getNotificationId())){
            log.info("Notification {} has already been processed.", event.getNotificationId());
            return;
        }
        if ("FAIL".equalsIgnoreCase(event.getTitle())) {
            throw new RuntimeException("Simulating sms service failure");
        }
        smsService.process(event);
        idempotencyService.markAsProcessed(event.getNotificationId());
        
    }

}

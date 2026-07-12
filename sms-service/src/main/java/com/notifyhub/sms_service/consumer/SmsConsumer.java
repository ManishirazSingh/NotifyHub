package com.notifyhub.sms_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notifyhub.common.dto.NotificationEventPayload;
import com.notifyhub.common.enums.NotificationType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.notifyhub.sms_service.service.SmsService;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsConsumer {
    private final SmsService smsService;
    private final ObjectMapper objectMapper;


    @KafkaListener(topics = "${app.kafka.notification-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String payload) {
        try{
            NotificationEventPayload event = objectMapper.readValue(payload, NotificationEventPayload.class);
            if(event.getType() != NotificationType.SMS) {
                return;
            }
            smsService.process(event);
        } catch (Exception e) {
            log.error("Error occurred while consuming sms notification event", e);
        }
    }

}

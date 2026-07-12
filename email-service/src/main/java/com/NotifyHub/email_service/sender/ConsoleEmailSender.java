package com.notifyhub.email_service.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.notifyhub.common.dto.NotificationEventPayload;
@Service
@Slf4j
public class ConsoleEmailSender implements EmailSender {

    @Override
    public void send(NotificationEventPayload payload) {
        log.info("Sending Notification with ID: {}", payload.getNotificationId());
        log.info("User Id: {}", payload.getUserId());
        log.info("Type: {}", payload.getType());
        log.info("Title: {}", payload.getTitle());
        log.info("Message: {}", payload.getMessage());
    }
}

package com.notifyhub.sms_service.sender;

import com.notifyhub.common.dto.NotificationEventPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsoleSmsSender implements SmsSender {

    @Override
    public void send(NotificationEventPayload payload) {
        log.info("Sending Notification with ID: {}", payload.getNotificationId());
        log.info("User Id: {}", payload.getUserId());
        log.info("Type: {}", payload.getType());
        log.info("Title: {}", payload.getTitle());
        log.info("Message: {}", payload.getMessage());

}
}

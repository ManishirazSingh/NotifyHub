package com.notifyhub.email_service.sender;

import com.notifyhub.common.dto.NotificationEventPayload;
public interface EmailSender {
    void send(NotificationEventPayload payload);

}

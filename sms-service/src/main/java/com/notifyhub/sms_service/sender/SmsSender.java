package com.notifyhub.sms_service.sender;

import com.notifyhub.common.dto.NotificationEventPayload;

public interface SmsSender {
    
    void send(NotificationEventPayload payload);
    }

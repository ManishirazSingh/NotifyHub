package com.NotifyHub.notification_service.service;

import com.NotifyHub.notification_service.dto.NotificationRequest;
import com.NotifyHub.notification_service.dto.NotificationResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationService {
    public NotificationResponse send(NotificationRequest req)
    {
        String id=UUID.randomUUID().toString();
        return new NotificationResponse(id,"SUCCESS");

    }
}
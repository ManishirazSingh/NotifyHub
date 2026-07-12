package com.NotifyHub.notification_service.dto;

import java.time.LocalDateTime;

import com.notifyhub.common.enums.NotificationStatus;
import com.notifyhub.common.enums.NotificationType;

public class NotificationResponse {

    private String notificationId;
    private NotificationStatus status;
    private NotificationType type;
    private LocalDateTime createdAt;

    public NotificationResponse(String notificationId, NotificationStatus status,NotificationType type, LocalDateTime createdAt) {
        this.notificationId = notificationId;
        this.status = status;
        this.type=type;
        this.createdAt=createdAt;
    }

    public String getNotificationId() { return notificationId; }
    public NotificationStatus getStatus() { return status; }
    public NotificationType getType() { return type;}
    public LocalDateTime getCreatedAt(){ return createdAt; }
}
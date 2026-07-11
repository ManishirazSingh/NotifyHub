package com.NotifyHub.notification_service.dto;

import com.NotifyHub.notification_service.enums.NotificationStatus;
import com.NotifyHub.notification_service.enums.NotificationType;

public class NotificationEventPayload {

    private String notificationId;
    private String userId;
    private NotificationType type;
    private NotificationStatus status;

    public NotificationEventPayload(String notificationId, String userId, NotificationType type, NotificationStatus status) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.type = type;
        this.status = status;
    }

    public String getNotificationId() { return notificationId; }
    public String getUserId() { return userId; }    
    public NotificationType getType() { return type; }
    public NotificationStatus getStatus() { return status; }

}

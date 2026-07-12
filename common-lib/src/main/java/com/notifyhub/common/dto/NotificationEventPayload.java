package com.notifyhub.common.dto;

import com.notifyhub.common.enums.NotificationStatus;
import com.notifyhub.common.enums.NotificationType;

public class NotificationEventPayload {

    private String notificationId;
    private String userId;
    private NotificationType type;
    private NotificationStatus status;
    private String message;
    private String title;

    public NotificationEventPayload(String notificationId, String userId, NotificationType type, NotificationStatus status, String message, String title) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.type = type;
        this.status = status;
        this.message = message;
        this.title = title;
    }

    public String getNotificationId() { return notificationId; }
    public String getUserId() { return userId; }    
    public NotificationType getType() { return type; }
    public NotificationStatus getStatus() { return status; }
    public String getMessage() { return message; }
    public String getTitle() { return title; }

}

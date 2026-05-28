package com.NotifyHub.notification_service.dto;

import com.NotifyHub.notification_service.enums.NotificationType;

public class NotificationRequest {

    private String userId;
    private NotificationType type;
    private String title;
    private String message;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
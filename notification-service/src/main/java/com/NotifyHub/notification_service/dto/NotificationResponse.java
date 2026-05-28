package com.NotifyHub.notification_service.dto;

public class NotificationResponse {

    private String notificationId;
    private String status;

    public NotificationResponse(String notificationId, String status) {
        this.notificationId = notificationId;
        this.status = status;
    }

    public String getNotificationId() { return notificationId; }
    public String getStatus() { return status; }
}
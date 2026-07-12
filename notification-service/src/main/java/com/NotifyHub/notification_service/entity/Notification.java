package com.NotifyHub.notification_service.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import com.notifyhub.common.enums.NotificationStatus;
import com.notifyhub.common.enums.NotificationType;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    private String id;

    private String userId;
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String title;
    private String message;
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Notification(){}
    public Notification(String userId,NotificationType type, String title, String message){
        this.id= UUID.randomUUID().toString();
        this.userId=userId;
        this.type=type;
        this.title=title;
        this.message=message;
        this.status=NotificationStatus.QUEUED;
    }
    public String getId(){
        return id;
    }
    public String getUserId(){
        return userId;
    }
    public NotificationStatus getStatus(){
        return status;
    }
    public String getTitle(){ return title;}
    public String getMessage(){ return message;}
    public NotificationType getType(){ return type;}
    public LocalDateTime getCreatedAt(){ return createdAt; }
    @PrePersist
    public void prePersist(){
        this.createdAt=LocalDateTime.now();
    }
}

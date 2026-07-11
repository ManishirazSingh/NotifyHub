package com.NotifyHub.notification_service.entity;

import com.NotifyHub.notification_service.enums.OutboxEventType;
import com.NotifyHub.notification_service.enums.OutboxStatus;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "outbox_events")
public class OutboxEvent {
    @Id 
    private String id;

    private String aggregateId;
    @Enumerated(EnumType.STRING)
    private OutboxEventType eventType;

    @Column(columnDefinition = "TEXT")
    private String payload;

    @Enumerated(EnumType.STRING)
    private OutboxStatus status;

    private LocalDateTime createdAt;

    public OutboxEvent() {
    }

    public OutboxEvent(String aggregateId, OutboxEventType eventType, String payload) {
        this.id = UUID.randomUUID().toString();
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.payload = payload;
        this.status = OutboxStatus.NEW;
    }

    public String getPayload() {
        return payload;
    }
    
    @PrePersist
    protected void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public void setStatus(OutboxStatus status) {
        this.status = status;
    }
}

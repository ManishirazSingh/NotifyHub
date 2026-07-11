package com.NotifyHub.notification_service.entity;

@Entity
@Table(name = "outbox_events")
public class OutboxEvent {
    @Id 
    private String id;

    private String aggregateId;

    private String eventType;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private String status;

    private LocalDateTime createdAt;

    public OutboxEvent() {
    }

    public OutboxEvent(String id, String aggregateId, String eventType, String payload, String status, LocalDateTime createdAt) {
        this.id = id;
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.payload = payload;
        this.status = status;
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}

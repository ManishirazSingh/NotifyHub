package com.NotifyHub.notification_service.service;

import com.NotifyHub.notification_service.dto.NotificationRequest;
import com.NotifyHub.notification_service.dto.NotificationResponse;
import com.NotifyHub.notification_service.entity.Notification;
import com.NotifyHub.notification_service.entity.OutboxEvent;
import com.NotifyHub.notification_service.exception.NotificationNotFoundException;
import com.NotifyHub.notification_service.repository.NotificationRepository;
import com.NotifyHub.notification_service.repository.OutboxEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notifyhub.common.dto.NotificationEventPayload;
import com.notifyhub.common.enums.OutboxEventType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationService {
    private final NotificationRepository repository;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    public NotificationService(NotificationRepository repository, OutboxEventRepository outboxEventRepository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.outboxEventRepository = outboxEventRepository;
        this.objectMapper = objectMapper;
    }
    @Transactional
    public NotificationResponse send(NotificationRequest req)
    {
        Notification notification=new Notification(req.getUserId(),req.getType(), req.getTitle(), req.getMessage());
        Notification saved =repository.save(notification);
        NotificationEventPayload payload = new NotificationEventPayload(saved.getId(), saved.getUserId(), saved.getType(), saved.getStatus(), saved.getMessage(), saved.getTitle());
        String payloadJson;
        try {
            payloadJson = objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize payload", e);
        }
        OutboxEvent outboxEvent = new OutboxEvent(saved.getId(),OutboxEventType.NOTIFICATION_CREATED,payloadJson);
        outboxEventRepository.save(outboxEvent);
        return new NotificationResponse(saved.getId(), saved.getStatus(),saved.getType(),saved.getCreatedAt());

    }
    public NotificationResponse getById(String id)
    {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));
        return new NotificationResponse(notification.getId(), notification.getStatus(),notification.getType(),notification.getCreatedAt());

    }
}
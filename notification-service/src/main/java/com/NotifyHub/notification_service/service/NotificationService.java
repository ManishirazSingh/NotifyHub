package com.NotifyHub.notification_service.service;

import com.NotifyHub.notification_service.dto.NotificationRequest;
import com.NotifyHub.notification_service.dto.NotificationResponse;
import com.NotifyHub.notification_service.entity.Notification;
import com.NotifyHub.notification_service.exception.NotificationNotFoundException;
import com.NotifyHub.notification_service.repository.NotificationRepository;
import com.NotifyHub.notification_service.repository.OutboxEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationService {
    private final NotificationRepository repository;
    private final OutboxEventRepository outboxEventRepository;
    public NotificationService(NotificationRepository repository, OutboxEventRepository outboxEventRepository)
    {
        this.repository=repository;
        this.outboxEventRepository=outboxEventRepository;
    }
    @Transactional
    public NotificationResponse send(NotificationRequest req)
    {
        Notification notification=new Notification(req.getUserId(),req.getType(), req.getTitle(), req.getMessage());
        Notification saved =repository.save(notification);
        return new NotificationResponse(saved.getId(), saved.getStatus(),saved.getType(),saved.getCreatedAt());

    }
    public NotificationResponse getById(String id)
    {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));
        return new NotificationResponse(notification.getId(), notification.getStatus(),notification.getType(),notification.getCreatedAt());

    }
}
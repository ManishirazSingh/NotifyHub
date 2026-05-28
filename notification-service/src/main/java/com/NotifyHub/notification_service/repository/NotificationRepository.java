package com.NotifyHub.notification_service.repository;

import com.NotifyHub.notification_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,String> {
}

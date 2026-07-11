package com.NotifyHub.notification_service.repository;

import com.NotifyHub.notification_service.entity.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxEventRepository extends JpaRepository<OutboxEvent,String> {
}

package com.NotifyHub.notification_service.service;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.NotifyHub.notification_service.entity.OutboxEvent;
import com.NotifyHub.notification_service.enums.OutboxStatus;
import com.NotifyHub.notification_service.repository.OutboxEventRepository;

import jakarta.transaction.Transactional;

@Service
public class OutboxWorker {
    private final OutboxEventRepository outboxEventRepository;

    public OutboxWorker(OutboxEventRepository outboxEventRepository) {
        this.outboxEventRepository = outboxEventRepository;
    }

    @Scheduled(fixedRate = 5000) // Runs every 5 seconds
    @Transactional
    public void processOutboxEvents() {
        List<OutboxEvent> events = outboxEventRepository.findByStatus(OutboxStatus.NEW);
        for(OutboxEvent event : events){
            System.out.println("Publishing Event : " + event.getPayload());
            event.setStatus(OutboxStatus.PROCESSED);
        }
    }
}

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
    private final KafkaProducerService kafkaProducerService;

    public OutboxWorker(OutboxEventRepository outboxEventRepository, KafkaProducerService kafkaProducerService) {
        this.outboxEventRepository = outboxEventRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Scheduled(fixedRate = 5000) // Runs every 5 seconds
    @Transactional
    public void processOutboxEvents() {
        List<OutboxEvent> events = outboxEventRepository.findByStatus(OutboxStatus.NEW);
        for(OutboxEvent event : events){
            try {
                kafkaProducerService.publish(event.getPayload());
                event.setStatus(OutboxStatus.PROCESSED);
            } catch (Exception e) {
                // Log the error and continue processing other events
                System.err.println("Failed to process event with ID: " + event.getId() + ". Error: " + e.getMessage());
                event.setStatus(OutboxStatus.FAILED);
            }
        }
    }
}

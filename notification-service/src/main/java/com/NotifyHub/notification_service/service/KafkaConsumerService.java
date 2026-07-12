package com.NotifyHub.notification_service.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
    
    @KafkaListener(topics = "${app.kafka.notification-topic}", groupId = "notification-group")
    
    public void consume(String message) {
        log.info("Consumed message: {}", message);
    }
}

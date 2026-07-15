package com.notifyhub.email_service.config;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {

    @Value("${app.kafka.notification-dlt-topic}")
    private String deadLetterTopic;

    @Bean
    public CommonErrorHandler errorHandler(KafkaTemplate<String, String> kafkaTemplate) {

        DeadLetterPublishingRecoverer recoverer =
                new DeadLetterPublishingRecoverer(
                        kafkaTemplate,
                        (record, ex) -> new TopicPartition(deadLetterTopic, record.partition())
                );

        return new DefaultErrorHandler(
                recoverer,
                new FixedBackOff(2000L, 2L)
        );
    }
}
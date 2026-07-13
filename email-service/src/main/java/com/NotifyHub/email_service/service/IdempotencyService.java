package com.notifyhub.email_service.service;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;

@Service
@RequiredArgsConstructor
public class IdempotencyService {
    private final StringRedisTemplate redisTemplate;
    private static final String PREFIX = "notification:processed:";

    public boolean isProcessed(String notificationId) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + notificationId));
    }

    public void markAsProcessed(String notificationId)
    {
        redisTemplate.opsForValue().set(PREFIX + notificationId,"DONE",Duration.ofHours(24));
    }
    

}

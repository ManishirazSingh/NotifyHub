package com.notifyhub.email_service.service;

import com.notifyhub.common.dto.NotificationEventPayload;
import com.notifyhub.email_service.sender.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailSender emailSender;
    public void process(NotificationEventPayload payload) {
        emailSender.send(payload);
    }
}

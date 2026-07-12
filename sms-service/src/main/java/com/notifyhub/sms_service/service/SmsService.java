package com.notifyhub.sms_service.service;

import org.springframework.stereotype.Service;

import com.notifyhub.common.dto.NotificationEventPayload;

import lombok.RequiredArgsConstructor;

import com.notifyhub.sms_service.sender.SmsSender;

@Service
@RequiredArgsConstructor
public class SmsService {
    private final SmsSender smsSender;
    public void process(NotificationEventPayload event) {
        smsSender.send(event);
    }
}

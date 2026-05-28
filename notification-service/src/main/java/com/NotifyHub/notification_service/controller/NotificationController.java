package com.NotifyHub.notification_service.controller;


import com.NotifyHub.notification_service.dto.NotificationRequest;
import com.NotifyHub.notification_service.dto.NotificationResponse;
import com.NotifyHub.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    public NotificationController(NotificationService service)
    {
        this.notificationService=service;
    }

    @PostMapping
    public NotificationResponse send(@RequestBody NotificationRequest request){
        return notificationService.send(request);
    }
}

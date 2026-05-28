package com.NotifyHub.notification_service.controller;


import com.NotifyHub.notification_service.dto.NotificationRequest;
import com.NotifyHub.notification_service.dto.NotificationResponse;
import com.NotifyHub.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    public NotificationController(NotificationService service)
    {
        this.notificationService=service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationResponse send(@Valid @RequestBody NotificationRequest request){
        return notificationService.send(request);
    }
    @GetMapping("/{id}")
    public NotificationResponse getNotification(@PathVariable String id){
        return notificationService.getById(id);
    }
}

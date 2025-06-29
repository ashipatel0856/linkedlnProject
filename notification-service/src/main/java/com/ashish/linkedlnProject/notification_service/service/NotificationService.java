package com.ashish.linkedlnProject.notification_service.service;

import com.ashish.linkedlnProject.notification_service.entity.Notification;
import com.ashish.linkedlnProject.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void addNotification(Notification notification) {
        log.info("Adding notification to database,message:",notification.getMessage());
    notification=notificationRepository.save(notification);

    // sendmailer to send email
    }

}

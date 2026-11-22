package com.enterprise.notification.exception;

public class NotificationTemplateNotFoundException extends ResourceNotFoundException {

    public NotificationTemplateNotFoundException(Long id) {
        super("Notification template not found with id: " + id);
    }
}

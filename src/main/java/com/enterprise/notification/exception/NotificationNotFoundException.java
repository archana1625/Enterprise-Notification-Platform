package com.enterprise.notification.exception;

public class NotificationNotFoundException extends ResourceNotFoundException {

    public NotificationNotFoundException(Long id) {
        super("Notification not found with id: " + id);
    }
}

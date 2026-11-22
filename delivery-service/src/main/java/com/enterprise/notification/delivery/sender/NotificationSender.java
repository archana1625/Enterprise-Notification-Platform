package com.enterprise.notification.delivery.sender;

public interface NotificationSender {

    NotificationChannel channel();

    void send(NotificationRequest request);
}

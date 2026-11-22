package com.enterprise.notification.delivery.sender;

public record NotificationRequest(
        Long notificationId,
        String recipient,
        NotificationChannel channel,
        String subject,
        String message,
        String priority) {
}

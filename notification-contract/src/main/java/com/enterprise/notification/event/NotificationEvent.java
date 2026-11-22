package com.enterprise.notification.event;

import java.time.Instant;

public record NotificationEvent(
        Long id,
        String recipient,
        String channel,
        String subject,
        String message,
        String priority,
        Instant createdAt) {
}

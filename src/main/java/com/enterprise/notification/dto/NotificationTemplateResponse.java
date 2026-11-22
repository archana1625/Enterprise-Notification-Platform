package com.enterprise.notification.dto;

import com.enterprise.notification.entity.Notification.Channel;
import java.time.Instant;

public record NotificationTemplateResponse(
        Long id,
        String templateName,
        Channel channel,
        String subjectTemplate,
        String bodyTemplate,
        boolean active,
        Instant createdAt
) {
}

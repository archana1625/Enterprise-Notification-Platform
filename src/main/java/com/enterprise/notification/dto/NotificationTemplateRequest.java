package com.enterprise.notification.dto;

import com.enterprise.notification.entity.Notification.Channel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NotificationTemplateRequest(
        @NotBlank(message = "templateName is required")
                @Size(max = 100, message = "templateName must not exceed 100 characters")
                String templateName,
        @NotNull(message = "channel is required") Channel channel,
        @NotBlank(message = "subjectTemplate is required")
                @Size(max = 10000, message = "subjectTemplate must not exceed 10000 characters")
                String subjectTemplate,
        @NotBlank(message = "bodyTemplate is required")
                @Size(max = 100000, message = "bodyTemplate must not exceed 100000 characters")
                String bodyTemplate,
        @NotNull(message = "active is required") Boolean active
) {
}

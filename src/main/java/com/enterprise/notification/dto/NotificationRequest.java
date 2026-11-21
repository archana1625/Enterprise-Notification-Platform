package com.enterprise.notification.dto;

import com.enterprise.notification.entity.Notification.Channel;
import com.enterprise.notification.entity.Notification.Priority;
import com.enterprise.notification.entity.Notification.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    @NotBlank(message = "recipient is required")
    @Size(max = 255, message = "recipient must not exceed 255 characters")
    private String recipient;

    @NotNull(message = "channel is required")
    private Channel channel;

    @NotBlank(message = "subject is required")
    @Size(max = 255, message = "subject must not exceed 255 characters")
    private String subject;

    @NotBlank(message = "message is required")
    private String message;

    @NotNull(message = "priority is required")
    private Priority priority;

    private Status status;
}

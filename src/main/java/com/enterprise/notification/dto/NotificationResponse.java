package com.enterprise.notification.dto;

import com.enterprise.notification.entity.Notification.Channel;
import com.enterprise.notification.entity.Notification.Priority;
import com.enterprise.notification.entity.Notification.Status;
import java.time.Instant;
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
public class NotificationResponse {
    private Long id;
    private String recipient;
    private Channel channel;
    private String subject;
    private String message;
    private Priority priority;
    private Status status;
    private Instant createdAt;
    private Instant updatedAt;
}

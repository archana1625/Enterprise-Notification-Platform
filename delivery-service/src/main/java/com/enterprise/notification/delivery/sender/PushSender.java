package com.enterprise.notification.delivery.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PushSender implements NotificationSender {

    private static final Logger log = LoggerFactory.getLogger(PushSender.class);

    @Override
    public NotificationChannel channel() {
        return NotificationChannel.PUSH;
    }

    @Override
    public void send(NotificationRequest request) {
        log.info("Push delivery requested for notification {} to {}",
                request.notificationId(), request.recipient());
    }
}

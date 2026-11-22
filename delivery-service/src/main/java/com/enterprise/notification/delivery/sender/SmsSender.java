package com.enterprise.notification.delivery.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SmsSender implements NotificationSender {

    private static final Logger log = LoggerFactory.getLogger(SmsSender.class);

    @Override
    public NotificationChannel channel() {
        return NotificationChannel.SMS;
    }

    @Override
    public void send(NotificationRequest request) {
        log.info("SMS delivery requested for notification {} to {}",
                request.notificationId(), request.recipient());
    }
}

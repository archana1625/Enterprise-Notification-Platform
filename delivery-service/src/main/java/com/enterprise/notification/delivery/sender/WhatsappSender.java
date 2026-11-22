package com.enterprise.notification.delivery.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WhatsappSender implements NotificationSender {

    private static final Logger log = LoggerFactory.getLogger(WhatsappSender.class);

    @Override
    public NotificationChannel channel() {
        return NotificationChannel.WHATSAPP;
    }

    @Override
    public void send(NotificationRequest request) {
        log.info("WhatsApp delivery requested for notification {} to {}",
                request.notificationId(), request.recipient());
    }
}

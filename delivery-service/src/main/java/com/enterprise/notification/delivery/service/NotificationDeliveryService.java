package com.enterprise.notification.delivery.service;

import com.enterprise.notification.delivery.sender.NotificationChannel;
import com.enterprise.notification.delivery.sender.NotificationRequest;
import com.enterprise.notification.delivery.sender.NotificationSenderFactory;
import com.enterprise.notification.event.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationDeliveryService {

    private static final Logger log = LoggerFactory.getLogger(NotificationDeliveryService.class);

    private final NotificationSenderFactory senderFactory;

    public NotificationDeliveryService(NotificationSenderFactory senderFactory) {
        this.senderFactory = senderFactory;
    }

    public void deliver(NotificationEvent event) {
        NotificationChannel channel;
        try {
            channel = NotificationChannel.valueOf(event.channel());
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "Unsupported notification channel: " + event.channel(), exception);
        }

        NotificationRequest request = new NotificationRequest(
                event.id(),
                event.recipient(),
                channel,
                event.subject(),
                event.message(),
                event.priority());
        log.info("Consumed notification {} for delivery through {}", event.id(), channel);
        senderFactory.getSender(channel).send(request);
    }
}

package com.enterprise.notification.delivery.service;

import com.enterprise.notification.event.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationDeliveryService {

    private static final Logger log = LoggerFactory.getLogger(NotificationDeliveryService.class);

    public void deliver(NotificationEvent event) {
        log.info("Consumed notification {} for delivery through {}",
                event.id(), event.channel());
    }
}

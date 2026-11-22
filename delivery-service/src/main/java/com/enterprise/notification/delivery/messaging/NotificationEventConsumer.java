package com.enterprise.notification.delivery.messaging;

import com.enterprise.notification.delivery.service.NotificationDeliveryService;
import com.enterprise.notification.event.NotificationEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventConsumer {

    private final NotificationDeliveryService deliveryService;

    public NotificationEventConsumer(NotificationDeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @KafkaListener(
            topics = "${app.kafka.topic.notification-created:notification-created}",
            groupId = "${spring.kafka.consumer.group-id:notification-delivery}")
    public void consume(NotificationEvent event) {
        deliveryService.deliver(event);
    }
}

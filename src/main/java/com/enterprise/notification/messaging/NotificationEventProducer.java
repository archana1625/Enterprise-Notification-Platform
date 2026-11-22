package com.enterprise.notification.messaging;

import com.enterprise.notification.event.NotificationEvent;
import java.util.concurrent.CompletionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationEventProducer {

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    @Value("${app.kafka.topic.notification-created:notification-created}")
    private String topic;

    public void publish(NotificationEvent event) {
        log.info("Publishing event to topic {}. Event payload: {}", topic, event);
        kafkaTemplate.send(topic, event.id().toString(), event)
                .whenComplete((result, error) -> logResult(event, result, error));
    }

    private void logResult(
            NotificationEvent event, SendResult<String, NotificationEvent> result, Throwable error) {
        if (error != null) {
            Throwable cause = error instanceof CompletionException && error.getCause() != null
                    ? error.getCause()
                    : error;
            log.error("Failed to publish event to topic {} for notification {}", topic, event.id(), cause);
            return;
        }
        log.info("Successfully published event. Partition={} Offset={}",
                result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
    }
}

package com.enterprise.notification.delivery.service;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.enterprise.notification.delivery.sender.NotificationChannel;
import com.enterprise.notification.delivery.sender.NotificationRequest;
import com.enterprise.notification.delivery.sender.NotificationSender;
import com.enterprise.notification.delivery.sender.NotificationSenderFactory;
import com.enterprise.notification.event.NotificationEvent;
import java.time.Instant;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class NotificationDeliveryServiceTest {

    private final NotificationSenderFactory senderFactory = mock(NotificationSenderFactory.class);
    private final NotificationDeliveryService deliveryService =
            new NotificationDeliveryService(senderFactory);
    private final Map<NotificationChannel, NotificationSender> senders =
            new EnumMap<>(NotificationChannel.class);

    @BeforeEach
    void setUp() {
        for (NotificationChannel channel : NotificationChannel.values()) {
            NotificationSender sender = mock(NotificationSender.class);
            senders.put(channel, sender);
            when(senderFactory.getSender(channel)).thenReturn(sender);
        }
    }

    @ParameterizedTest
    @EnumSource(NotificationChannel.class)
    void convertsEventAndDispatchesToSenderForItsChannel(NotificationChannel channel) {
        NotificationEvent event = new NotificationEvent(
                7L,
                "recipient@example.com",
                channel.name(),
                "Welcome",
                "Hello",
                "HIGH",
                Instant.parse("2026-01-01T00:00:00Z"));

        deliveryService.deliver(event);

        verify(senders.get(channel)).send(new NotificationRequest(
                7L,
                "recipient@example.com",
                channel,
                "Welcome",
                "Hello",
                "HIGH"));
    }

    @Test
    void rejectsUnsupportedEventChannel() {
        NotificationEvent event = new NotificationEvent(
                7L, "recipient@example.com", "IN_APP", "Welcome", "Hello", "HIGH", Instant.now());

        assertThatIllegalArgumentException()
                .isThrownBy(() -> deliveryService.deliver(event))
                .withMessage("Unsupported notification channel: IN_APP");
    }
}

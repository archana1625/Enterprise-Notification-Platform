package com.enterprise.notification.delivery.sender;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class NotificationSenderFactory {

    private final Map<NotificationChannel, NotificationSender> senders;

    public NotificationSenderFactory(List<NotificationSender> senders) {
        this.senders = senders.stream().collect(Collectors.toUnmodifiableMap(
                NotificationSender::channel, Function.identity()));
    }

    public NotificationSender getSender(NotificationChannel channel) {
        NotificationSender sender = senders.get(channel);
        if (sender == null) {
            throw new IllegalArgumentException("No notification sender registered for channel " + channel);
        }
        return sender;
    }
}

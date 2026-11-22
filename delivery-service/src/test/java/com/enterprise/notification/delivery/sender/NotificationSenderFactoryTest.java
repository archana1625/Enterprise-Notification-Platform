package com.enterprise.notification.delivery.sender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;

class NotificationSenderFactoryTest {

    @Test
    void selectsSenderForEachSupportedChannel() {
        List<NotificationSender> senders = List.of(
                senderFor(NotificationChannel.EMAIL),
                senderFor(NotificationChannel.SMS),
                senderFor(NotificationChannel.WHATSAPP),
                senderFor(NotificationChannel.PUSH));
        NotificationSenderFactory factory = new NotificationSenderFactory(senders);

        for (NotificationSender sender : senders) {
            assertThat(factory.getSender(sender.channel())).isSameAs(sender);
        }
    }

    @Test
    void failsClearlyWhenNoSenderIsRegistered() {
        NotificationSenderFactory factory = new NotificationSenderFactory(List.of());

        assertThatIllegalArgumentException()
                .isThrownBy(() -> factory.getSender(NotificationChannel.EMAIL))
                .withMessage("No notification sender registered for channel EMAIL");
    }

    private NotificationSender senderFor(NotificationChannel channel) {
        NotificationSender sender = mock(NotificationSender.class);
        when(sender.channel()).thenReturn(channel);
        return sender;
    }
}

package com.enterprise.notification.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.enterprise.notification.dto.RenderedMessage;
import com.enterprise.notification.entity.NotificationTemplate;
import com.enterprise.notification.exception.TemplateRenderingException;
import com.enterprise.notification.util.TemplateVariableReplacer;
import java.util.Map;
import org.junit.jupiter.api.Test;

class NotificationTemplateRenderingServiceTest {

    private final NotificationTemplateRenderingService service =
            new NotificationTemplateRenderingService(new TemplateVariableReplacer());

    @Test
    void rendersSubjectAndBodyPlaceholders() {
        NotificationTemplate template = NotificationTemplate.builder()
                .subjectTemplate("Order {{orderId}}")
                .bodyTemplate("Hello {{name}}, order {{orderId}}")
                .build();

        assertThat(service.render(template, Map.of("name", "Ava", "orderId", "ORD-42")))
                .isEqualTo(new RenderedMessage(
                        "Order ORD-42", "Hello Ava, order ORD-42"));
    }

    @Test
    void rejectsMissingPlaceholderValues() {
        NotificationTemplate template = NotificationTemplate.builder()
                .subjectTemplate("Order {{orderId}}")
                .bodyTemplate("Hello {{name}}")
                .build();

        assertThatThrownBy(() -> service.render(template, Map.of("name", "Ava")))
                .isInstanceOf(TemplateRenderingException.class)
                .hasMessageContaining("orderId");
    }
}

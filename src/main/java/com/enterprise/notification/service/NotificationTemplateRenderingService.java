package com.enterprise.notification.service;

import com.enterprise.notification.dto.RenderedMessage;
import com.enterprise.notification.entity.NotificationTemplate;
import com.enterprise.notification.util.TemplateVariableReplacer;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationTemplateRenderingService {

    private final TemplateVariableReplacer variableReplacer;

    public RenderedMessage render(NotificationTemplate template, Map<String, String> variables) {
        return new RenderedMessage(
                variableReplacer.replace(template.getSubjectTemplate(), variables),
                variableReplacer.replace(template.getBodyTemplate(), variables));
    }
}

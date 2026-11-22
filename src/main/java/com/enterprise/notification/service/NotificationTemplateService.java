package com.enterprise.notification.service;

import com.enterprise.notification.dto.NotificationTemplateRequest;
import com.enterprise.notification.dto.NotificationTemplateResponse;
import com.enterprise.notification.dto.RenderedMessage;
import java.util.List;
import java.util.Map;

public interface NotificationTemplateService {

    NotificationTemplateResponse create(NotificationTemplateRequest request);

    List<NotificationTemplateResponse> getAll();

    NotificationTemplateResponse getById(Long id);

    NotificationTemplateResponse update(Long id, NotificationTemplateRequest request);

    void delete(Long id);

    RenderedMessage render(Long id, Map<String, String> variables);
}

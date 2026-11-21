package com.enterprise.notification.service;

import com.enterprise.notification.dto.NotificationRequest;
import com.enterprise.notification.dto.NotificationResponse;
import java.util.List;

public interface NotificationService {
    NotificationResponse create(NotificationRequest request);

    List<NotificationResponse> getAll();

    NotificationResponse getById(Long id);

    NotificationResponse update(Long id, NotificationRequest request);

    void delete(Long id);
}

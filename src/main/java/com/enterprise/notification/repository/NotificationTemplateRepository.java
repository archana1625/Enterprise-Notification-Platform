package com.enterprise.notification.repository;

import com.enterprise.notification.entity.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTemplateRepository
        extends JpaRepository<NotificationTemplate, Long> {
}

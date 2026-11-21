package com.enterprise.notification.service;

import com.enterprise.notification.dto.NotificationRequest;
import com.enterprise.notification.dto.NotificationResponse;
import com.enterprise.notification.entity.Notification;
import com.enterprise.notification.exception.NotificationNotFoundException;
import com.enterprise.notification.mapper.NotificationMapper;
import com.enterprise.notification.repository.NotificationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final NotificationMapper mapper;

    @Override
    @Transactional
    public NotificationResponse create(NotificationRequest request) {
        Notification notification = mapper.toEntity(request);
        if (notification.getStatus() == null) {
            notification.setStatus(Notification.Status.PENDING);
        }
        return mapper.toResponse(repository.save(notification));
    }

    @Override
    public List<NotificationResponse> getAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public NotificationResponse getById(Long id) {
        return mapper.toResponse(findById(id));
    }

    @Override
    @Transactional
    public NotificationResponse update(Long id, NotificationRequest request) {
        Notification notification = findById(id);
        mapper.updateEntity(request, notification);
        if (notification.getStatus() == null) {
            notification.setStatus(Notification.Status.PENDING);
        }
        return mapper.toResponse(repository.save(notification));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotificationNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private Notification findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));
    }
}

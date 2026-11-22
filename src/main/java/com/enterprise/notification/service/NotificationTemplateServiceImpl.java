package com.enterprise.notification.service;

import com.enterprise.notification.dto.NotificationTemplateRequest;
import com.enterprise.notification.dto.NotificationTemplateResponse;
import com.enterprise.notification.dto.RenderedMessage;
import com.enterprise.notification.entity.NotificationTemplate;
import com.enterprise.notification.exception.NotificationTemplateNotFoundException;
import com.enterprise.notification.mapper.NotificationTemplateMapper;
import com.enterprise.notification.repository.NotificationTemplateRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationTemplateServiceImpl implements NotificationTemplateService {

    private final NotificationTemplateRepository repository;
    private final NotificationTemplateMapper mapper;
    private final NotificationTemplateRenderingService renderingService;

    @Override
    @Transactional
    public NotificationTemplateResponse create(NotificationTemplateRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    @Override
    public List<NotificationTemplateResponse> getAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public NotificationTemplateResponse getById(Long id) {
        return mapper.toResponse(findById(id));
    }

    @Override
    @Transactional
    public NotificationTemplateResponse update(Long id, NotificationTemplateRequest request) {
        NotificationTemplate template = findById(id);
        mapper.updateEntity(request, template);
        return mapper.toResponse(repository.save(template));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.delete(findById(id));
    }

    @Override
    public RenderedMessage render(Long id, Map<String, String> variables) {
        return renderingService.render(findById(id), variables);
    }

    private NotificationTemplate findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotificationTemplateNotFoundException(id));
    }
}

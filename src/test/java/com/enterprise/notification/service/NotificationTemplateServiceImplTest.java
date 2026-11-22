package com.enterprise.notification.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.enterprise.notification.dto.NotificationTemplateRequest;
import com.enterprise.notification.dto.NotificationTemplateResponse;
import com.enterprise.notification.dto.RenderedMessage;
import com.enterprise.notification.entity.Notification;
import com.enterprise.notification.entity.NotificationTemplate;
import com.enterprise.notification.exception.NotificationTemplateNotFoundException;
import com.enterprise.notification.mapper.NotificationTemplateMapper;
import com.enterprise.notification.repository.NotificationTemplateRepository;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationTemplateServiceImplTest {

    @Mock
    private NotificationTemplateRepository repository;

    @Mock
    private NotificationTemplateMapper mapper;

    @Mock
    private NotificationTemplateRenderingService renderingService;

    @InjectMocks
    private NotificationTemplateServiceImpl service;

    @Test
    void createPersistsAndReturnsMappedTemplate() {
        NotificationTemplateRequest request = request();
        NotificationTemplate template = template();
        NotificationTemplateResponse response = response();
        when(mapper.toEntity(request)).thenReturn(template);
        when(repository.save(template)).thenReturn(template);
        when(mapper.toResponse(template)).thenReturn(response);

        assertThat(service.create(request)).isSameAs(response);
        verify(repository).save(template);
    }

    @Test
    void getAllReturnsMappedTemplates() {
        NotificationTemplate template = template();
        NotificationTemplateResponse response = response();
        when(repository.findAll()).thenReturn(java.util.List.of(template));
        when(mapper.toResponse(template)).thenReturn(response);

        assertThat(service.getAll()).containsExactly(response);
    }

    @Test
    void getByIdThrowsWhenTemplateDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99L))
                .isInstanceOf(NotificationTemplateNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void updateMapsRequestIntoExistingTemplate() {
        NotificationTemplateRequest request = request();
        NotificationTemplate template = template();
        NotificationTemplateResponse response = response();
        when(repository.findById(1L)).thenReturn(Optional.of(template));
        when(repository.save(template)).thenReturn(template);
        when(mapper.toResponse(template)).thenReturn(response);

        assertThat(service.update(1L, request)).isSameAs(response);
        verify(mapper).updateEntity(request, template);
    }

    @Test
    void deleteRemovesExistingTemplate() {
        NotificationTemplate template = template();
        when(repository.findById(1L)).thenReturn(Optional.of(template));

        service.delete(1L);

        verify(repository).delete(template);
    }

    @Test
    void deleteThrowsWhenTemplateDoesNotExist() {
        when(repository.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(42L))
                .isInstanceOf(NotificationTemplateNotFoundException.class);
    }

    @Test
    void renderLooksUpTemplateAndDelegatesVariables() {
        NotificationTemplate template = template();
        Map<String, String> variables = Map.of("name", "Ada");
        RenderedMessage rendered = new RenderedMessage("Welcome Ada", "Hello Ada");
        when(repository.findById(1L)).thenReturn(Optional.of(template));
        when(renderingService.render(template, variables)).thenReturn(rendered);

        assertThat(service.render(1L, variables)).isSameAs(rendered);
    }

    private NotificationTemplateRequest request() {
        return new NotificationTemplateRequest(
                "welcome", Notification.Channel.EMAIL, "Welcome {{name}}", "Hello {{name}}", true);
    }

    private NotificationTemplate template() {
        return NotificationTemplate.builder()
                .id(1L)
                .templateName("welcome")
                .channel(Notification.Channel.EMAIL)
                .subjectTemplate("Welcome {{name}}")
                .bodyTemplate("Hello {{name}}")
                .active(true)
                .createdAt(Instant.now())
                .build();
    }

    private NotificationTemplateResponse response() {
        return new NotificationTemplateResponse(
                1L, "welcome", Notification.Channel.EMAIL,
                "Welcome {{name}}", "Hello {{name}}", true, Instant.now());
    }
}

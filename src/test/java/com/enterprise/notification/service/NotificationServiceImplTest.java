package com.enterprise.notification.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.enterprise.notification.dto.NotificationRequest;
import com.enterprise.notification.dto.NotificationResponse;
import com.enterprise.notification.entity.Notification;
import com.enterprise.notification.exception.NotificationNotFoundException;
import com.enterprise.notification.mapper.NotificationMapper;
import com.enterprise.notification.repository.NotificationRepository;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository repository;

    @Mock
    private NotificationMapper mapper;

    @InjectMocks
    private NotificationServiceImpl service;

    @Test
    void createSetsPendingStatusAndMapsResponse() {
        NotificationRequest request = new NotificationRequest();
        request.setRecipient("user@example.com");
        Notification entity = new Notification();
        NotificationResponse response = new NotificationResponse();

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.create(request)).isSameAs(response);
        assertThat(entity.getStatus()).isEqualTo(Notification.Status.PENDING);
        verify(repository).save(entity);
    }

    @Test
    void getByIdThrowsCustomExceptionWhenMissing() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99L))
                .isInstanceOf(NotificationNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void updateExistingNotification() {
        NotificationRequest request = new NotificationRequest();
        Notification entity = Notification.builder()
                .id(1L)
                .recipient("old")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        NotificationResponse response = NotificationResponse.builder().id(1L).build();

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        assertThat(service.update(1L, request)).isSameAs(response);
        verify(mapper).updateEntity(request, entity);
    }

    @Test
    void deleteThrowsWhenNotificationDoesNotExist() {
        when(repository.existsById(42L)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(42L))
                .isInstanceOf(NotificationNotFoundException.class);
    }
}

package com.enterprise.notification.controller;

import com.enterprise.notification.dto.NotificationTemplateRequest;
import com.enterprise.notification.dto.NotificationTemplateResponse;
import com.enterprise.notification.dto.RenderedMessage;
import com.enterprise.notification.dto.TemplateRenderRequest;
import com.enterprise.notification.exception.ApiResponse;
import com.enterprise.notification.service.NotificationTemplateService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification-templates")
@RequiredArgsConstructor
public class NotificationTemplateController {

    private final NotificationTemplateService service;

    @PostMapping
    public ResponseEntity<ApiResponse<NotificationTemplateResponse>> create(
            @Valid @RequestBody NotificationTemplateRequest request) {
        NotificationTemplateResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Notification template created", response));
    }

    @GetMapping
    public ApiResponse<List<NotificationTemplateResponse>> getAll() {
        return ApiResponse.success("Notification templates retrieved", service.getAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<NotificationTemplateResponse> getById(@PathVariable Long id) {
        return ApiResponse.success("Notification template retrieved", service.getById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<NotificationTemplateResponse> update(
            @PathVariable Long id, @Valid @RequestBody NotificationTemplateRequest request) {
        return ApiResponse.success("Notification template updated", service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/render")
    public ApiResponse<RenderedMessage> render(
            @PathVariable Long id, @Valid @RequestBody TemplateRenderRequest request) {
        return ApiResponse.success(
                "Notification template rendered", service.render(id, request.variables()));
    }
}

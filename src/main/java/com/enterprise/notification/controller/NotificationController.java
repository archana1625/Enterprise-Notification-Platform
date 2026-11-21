package com.enterprise.notification.controller;

import com.enterprise.notification.dto.NotificationRequest;
import com.enterprise.notification.dto.NotificationResponse;
import com.enterprise.notification.exception.ApiResponse;
import com.enterprise.notification.service.NotificationService;
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
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    public ResponseEntity<ApiResponse<NotificationResponse>> create(
            @Valid @RequestBody NotificationRequest request) {
        NotificationResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Notification created", response));
    }

    @GetMapping
    public ApiResponse<List<NotificationResponse>> getAll() {
        return ApiResponse.success("Notifications retrieved", service.getAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<NotificationResponse> getById(@PathVariable Long id) {
        return ApiResponse.success("Notification retrieved", service.getById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<NotificationResponse> update(
            @PathVariable Long id, @Valid @RequestBody NotificationRequest request) {
        return ApiResponse.success("Notification updated", service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

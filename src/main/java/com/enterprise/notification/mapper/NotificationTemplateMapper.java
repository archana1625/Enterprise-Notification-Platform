package com.enterprise.notification.mapper;

import com.enterprise.notification.dto.NotificationTemplateRequest;
import com.enterprise.notification.dto.NotificationTemplateResponse;
import com.enterprise.notification.entity.NotificationTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NotificationTemplateMapper {

    NotificationTemplateResponse toResponse(NotificationTemplate template);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    NotificationTemplate toEntity(NotificationTemplateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(
            NotificationTemplateRequest request, @MappingTarget NotificationTemplate template);
}

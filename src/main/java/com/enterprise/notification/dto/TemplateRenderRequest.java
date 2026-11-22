package com.enterprise.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record TemplateRenderRequest(
        @NotNull(message = "variables are required")
                Map<@NotBlank(message = "variable names must not be blank")
                        String, @NotNull(message = "variable values must not be null") String> variables
) {
}

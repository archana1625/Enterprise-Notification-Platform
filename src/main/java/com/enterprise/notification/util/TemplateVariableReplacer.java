package com.enterprise.notification.util;

import com.enterprise.notification.exception.TemplateRenderingException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class TemplateVariableReplacer {

    private static final Pattern PLACEHOLDER = Pattern.compile("\\{\\{([A-Za-z][A-Za-z0-9_]*)}}");

    public String replace(String template, Map<String, String> variables) {
        Matcher matcher = PLACEHOLDER.matcher(template);
        StringBuilder rendered = new StringBuilder();
        while (matcher.find()) {
            String variableName = matcher.group(1);
            if (!variables.containsKey(variableName)) {
                throw new TemplateRenderingException(
                        "Missing value for template variable: " + variableName);
            }
            String value = variables.get(variableName);
            if (value == null) {
                throw new TemplateRenderingException(
                        "Template variable must not be null: " + variableName);
            }
            matcher.appendReplacement(rendered, Matcher.quoteReplacement(value));
        }
        matcher.appendTail(rendered);
        return rendered.toString();
    }
}

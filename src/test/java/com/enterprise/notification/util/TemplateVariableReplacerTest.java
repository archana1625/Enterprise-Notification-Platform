package com.enterprise.notification.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.enterprise.notification.exception.TemplateRenderingException;
import java.util.Map;
import org.junit.jupiter.api.Test;

class TemplateVariableReplacerTest {

    private final TemplateVariableReplacer replacer = new TemplateVariableReplacer();

    @Test
    void replacesRepeatedPlaceholdersAndPreservesReplacementCharacters() {
        assertThat(replacer.replace(
                        "{{name}} purchased {{item}} from {{name}}",
                        Map.of("name", "$Ada\\Lovelace", "item", "a book")))
                .isEqualTo("$Ada\\Lovelace purchased a book from $Ada\\Lovelace");
    }

    @Test
    void preservesTextWithoutPlaceholders() {
        assertThat(replacer.replace("No variables here", Map.of()))
                .isEqualTo("No variables here");
    }

    @Test
    void rejectsMissingVariables() {
        assertThatThrownBy(() -> replacer.replace("Hello {{name}}", Map.of()))
                .isInstanceOf(TemplateRenderingException.class)
                .hasMessageContaining("name");
    }
}

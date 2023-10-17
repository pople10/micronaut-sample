/* Mohammed Amine AYACHE (C)2022 */
package com.sample.utils;

import io.micronaut.context.LocalizedMessageSource;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class LocalizationUtils {
    @Inject private LocalizedMessageSource messageSource;

    public String getMessage(String message) {
        return messageSource.getMessageOrDefault(message, message);
    }

    public Set<String> getMessages(Set<String> messages) {
        if (messages == null) return new HashSet<>();
        return messages.stream().map(this::getMessage).collect(Collectors.toSet());
    }
}

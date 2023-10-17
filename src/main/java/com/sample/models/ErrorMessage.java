/* Mohammed Amine AYACHE (C)2022 */
package com.sample.models;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.context.ServerRequestContext;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Introspected
@Builder
public class ErrorMessage {
    private String message;
    private Set<String> subMessages;
    private String cause;
    private Integer code;
    private String self;

    public String getSelf() {
        Optional<HttpRequest<Object>> currentRequest = ServerRequestContext.currentRequest();
        return self == null
                ? (currentRequest.isPresent() ? currentRequest.get().getPath() : null)
                : self;
    }

    public void addSubMessages(Throwable[] throwables) {
        if (subMessages == null) subMessages = new HashSet<>();
        for (Throwable throwable : throwables) {
            subMessages.add(throwable.getLocalizedMessage());
        }
    }
}

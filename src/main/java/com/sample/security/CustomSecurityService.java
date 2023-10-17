/* Mohammed Amine AYACHE (C)2022 */
package com.sample.security;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.context.ServerRequestContext;
import io.micronaut.runtime.http.scope.RequestScope;
import java.security.Principal;
import java.util.Optional;

@RequestScope
public class CustomSecurityService {
    private HttpRequest<?> source = ServerRequestContext.currentRequest().orElse(null);

    public Optional<String> username() {
        if (source == null) return Optional.empty();
        return source.getUserPrincipal().map(Principal::getName);
    }

    public boolean isAuthenticated() {
        return username().isPresent();
    }
}

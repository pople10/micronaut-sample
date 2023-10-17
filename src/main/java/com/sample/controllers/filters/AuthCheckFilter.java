/* Mohammed Amine AYACHE (C)2022 */
package com.sample.controllers.filters;

import com.sample.exceptions.AuthenticationFailedException;
import com.sample.exceptions.AuthorizationFailedException;
import com.sample.services.UserService;
import com.sample.utils.enumerations.EntityStatus;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.FilterChain;
import io.micronaut.http.filter.HttpFilter;
import io.micronaut.http.filter.ServerFilterPhase;
import java.security.Principal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import com.sample.repositories.entities.User;
import org.reactivestreams.Publisher;

@Filter("/api/**")
@RequiredArgsConstructor
public class AuthCheckFilter implements HttpFilter {
    private final UserService userService;

    @Override
    public Publisher<? extends HttpResponse<?>> doFilter(
            HttpRequest<?> request, FilterChain chain) {
        Optional<Principal> optionalPrincipal = request.getUserPrincipal();
        if (optionalPrincipal.isPresent()) {
            String email = request.getUserPrincipal().get().getName();
            Optional<User> userOptional = userService.findUserByEmail(email);
            if (!userOptional.isPresent()) throw new AuthenticationFailedException();
            User user = userOptional.get();
            if (EntityStatus.excludedStatus.contains(user.getStatus()))
                throw new AuthorizationFailedException();
        }
        return chain.proceed(request);
    }

    @Override
    public int getOrder() {
        return ServerFilterPhase.SECURITY.after();
    }
}

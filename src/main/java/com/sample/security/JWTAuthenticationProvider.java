/* Mohammed Amine AYACHE (C)2022 */
package com.sample.security;

import com.sample.utils.enumerations.EntityStatus;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import com.sample.exceptions.AuthenticationFailedException;
import com.sample.exceptions.UserBlockedException;
import com.sample.processes.UserProcess;
import com.sample.repositories.entities.Role;
import com.sample.repositories.entities.User;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Singleton
@RequiredArgsConstructor
public class JWTAuthenticationProvider implements AuthenticationProvider {
    private final UserProcess userProcess;

    @Override
    public Publisher<AuthenticationResponse> authenticate(
            HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        Optional<User> optionalUser =
                userProcess.getUserByCredentials(authenticationRequest.getIdentity().toString());
        return Flux.create(
                emitter -> {
                    if (optionalUser.isPresent()
                            && userProcess.isCredentialCorrect(
                                    optionalUser.get(),
                                    authenticationRequest.getSecret().toString())) {
                        User user = optionalUser.get();
                        if (EntityStatus.excludedStatus.contains(user.getStatus())) {
                            emitter.error(new UserBlockedException());
                            return;
                        }
                        emitter.next(
                                AuthenticationResponse.success(
                                        (String) authenticationRequest.getIdentity(),
                                        user.getRoles().stream()
                                                .map(Role::getCode)
                                                .collect(Collectors.toSet()),
                                        CollectionUtils.mapOf(
                                                "firstName",
                                                user.getFirstName(),
                                                "lastName",
                                                user.getLastName(),
                                                "email",
                                                user.getEmail())));
                        emitter.complete();
                    } else {
                        emitter.error(new AuthenticationFailedException());
                    }
                },
                FluxSink.OverflowStrategy.ERROR);
    }
}

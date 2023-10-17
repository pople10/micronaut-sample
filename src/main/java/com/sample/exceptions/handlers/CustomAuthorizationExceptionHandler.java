package com.sample.exceptions.handlers;

import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.security.authentication.AuthorizationException;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import com.sample.exceptions.AuthorizationFailedException;
import com.sample.models.ErrorMessage;
import com.sample.utils.LocalizationUtils;
import com.sample.utils.consts.ExceptionCode;

@Produces
@Singleton
@Primary
@RequiredArgsConstructor
@Requires(classes = {AuthorizationException.class, ExceptionHandler.class})
public class CustomAuthorizationExceptionHandler implements ExceptionHandler<AuthorizationException, HttpResponse> {
    private final LocalizationUtils localizationUtils;

    @Override
    public HttpResponse handle(HttpRequest request, AuthorizationException old) {
        AuthorizationFailedException exception = new AuthorizationFailedException();
        ErrorMessage message =
                ErrorMessage.builder()
                        .message(localizationUtils.getMessage(exception.getMessage()))
                        .code(ExceptionCode.FORBIDDEN.getCode())
                        .build();
        return HttpResponse.status(HttpStatus.FORBIDDEN).body(message);
    }
}

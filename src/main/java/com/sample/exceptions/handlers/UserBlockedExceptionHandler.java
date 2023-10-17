/* Mohammed Amine AYACHE (C)2022 */
package com.sample.exceptions.handlers;

import com.sample.exceptions.UserBlockedException;
import com.sample.models.ErrorMessage;
import com.sample.utils.LocalizationUtils;
import com.sample.utils.consts.ExceptionCode;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Produces
@Singleton
@RequiredArgsConstructor
@Requires(classes = {UserBlockedException.class, ExceptionHandler.class})
public class UserBlockedExceptionHandler
        implements ExceptionHandler<UserBlockedException, HttpResponse> {
    private final LocalizationUtils localizationUtils;

    private static final Logger logger =
            LoggerFactory.getLogger(AuthenticationFailedExceptionHandler.class);

    @Override
    public HttpResponse handle(HttpRequest request, UserBlockedException exception) {
        logger.debug(
                "{} Handler got exception {} for request {}",
                getClass().getName(),
                exception.getMessage(),
                request.getPath());
        exception.printStackTrace();
        ErrorMessage message =
                ErrorMessage.builder()
                        .message(localizationUtils.getMessage(exception.getMessage()))
                        .code(ExceptionCode.UNAUTHENTICATED.getCode())
                        .build();
        return HttpResponse.status(HttpStatus.UNAUTHORIZED).body(message);
    }
}

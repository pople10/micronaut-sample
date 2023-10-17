/* Mohammed Amine AYACHE (C)2022 */
package com.sample.exceptions.handlers;

import com.sample.exceptions.GenericApplicationException;
import com.sample.models.ErrorMessage;
import com.sample.utils.LocalizationUtils;
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
@Requires(classes = {GenericApplicationException.class, ExceptionHandler.class})
public class GenericExceptionHandler
        implements ExceptionHandler<GenericApplicationException, HttpResponse> {
    private final LocalizationUtils localizationUtils;

    private static final Logger logger = LoggerFactory.getLogger(GenericExceptionHandler.class);

    @Override
    public HttpResponse handle(HttpRequest request, GenericApplicationException exception) {
        logger.debug(
                "{} Handler got exception {} for request {}",
                getClass().getName(),
                exception.getMessage(),
                request.getPath());
        exception.printStackTrace();
        ErrorMessage message =
                ErrorMessage.builder()
                        .message(localizationUtils.getMessage(exception.getMessage()))
                        .cause(
                                exception.getCause() != null
                                        ? exception.getCause().getMessage()
                                        : null)
                        .subMessages(localizationUtils.getMessages(exception.getMessages()))
                        .code(exception.getCode())
                        .build();
        return HttpResponse.status(HttpStatus.valueOf(message.getCode())).body(message);
    }
}

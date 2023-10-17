package com.sample.exceptions.handlers;

import com.sample.exceptions.RedirectException;
import com.sample.models.RedirectionAction;
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
@Requires(classes = {RedirectException.class, ExceptionHandler.class})
public class RedirectExceptionExceptionHandler implements ExceptionHandler<RedirectException, HttpResponse> {
    private static final Logger logger = LoggerFactory.getLogger(RedirectExceptionExceptionHandler.class);

    @Override
    public HttpResponse handle(HttpRequest request, RedirectException exception) {
        logger.debug(
                "{} Handler got exception {} for request {}",
                getClass().getName(),
                exception.getMessage(),
                request.getPath());
        exception.printStackTrace();
        RedirectionAction redirectionAction = RedirectionAction
                .builder()
                .link(exception.getMessage())
                .build();
        return HttpResponse.status(HttpStatus.PAYMENT_REQUIRED).body(redirectionAction);
    }
}

/* Mohammed Amine AYACHE (C)2022 */
package com.sample.exceptions.handlers;

import com.sample.models.ErrorMessage;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import javax.persistence.PersistenceException;

@Produces
@Singleton
@Requires(classes = PersistenceException.class)
public class HibernateExceptionHandler
        implements ExceptionHandler<PersistenceException, HttpResponse> {
    @Override
    public HttpResponse handle(HttpRequest request, PersistenceException exception) {
        exception.printStackTrace();
        ErrorMessage error =
                ErrorMessage.builder()
                        .message(
                                exception.getCause() != null
                                        ? exception.getCause().getLocalizedMessage()
                                        : exception.getLocalizedMessage())
                        .self(request.getPath())
                        .code(HttpStatus.NO_RESPONSE.getCode())
                        .build();
        return HttpResponse.badRequest(error);
    }
}

/* Mohammed Amine AYACHE (C)2022 */
package com.sample.controllers.errors;

import com.sample.models.ErrorMessage;
import com.sample.utils.LocalizationUtils;
import com.sample.utils.consts.ResponseMessages;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ErrorController {
    private final LocalizationUtils localizationUtils;

    @Error(status = HttpStatus.NOT_FOUND, global = true)
    public HttpResponse<ErrorMessage> notFound(HttpRequest request) {
        ErrorMessage error =
                ErrorMessage.builder()
                        .message(localizationUtils.getMessage(ResponseMessages.PAGE_NOT_FOUND))
                        .code(HttpStatus.NOT_FOUND.getCode())
                        .self(request.getPath())
                        .build();

        return HttpResponse.<ErrorMessage>notFound().body(error); //
    }

    @Error(exception = ConstraintViolationException.class, global = true)
    public HttpResponse<ErrorMessage> validationError(
            ConstraintViolationException exception, HttpRequest request) {
        ErrorMessage error =
                ErrorMessage.builder()
                        .message(localizationUtils.getMessage(ResponseMessages.VALIDATION_ERROR))
                        .subMessages(
                                localizationUtils.getMessages(
                                        exception.getConstraintViolations().stream()
                                                .map(
                                                        e ->
                                                                String.format(
                                                                        "%s: " + e.getMessage(),
                                                                        localizationUtils
                                                                                .getMessage(
                                                                                        getNameFromPath(
                                                                                                e
                                                                                                        .getPropertyPath()))))
                                                .collect(Collectors.toSet())))
                        .self(request.getPath())
                        .cause(
                                exception.getCause() != null
                                        ? exception.getCause().getMessage()
                                        : null)
                        .code(HttpStatus.BAD_REQUEST.getCode())
                        .build();
        return HttpResponse.badRequest(error);
    }

    private static String getNameFromPath(Path path) {
        String field = null;
        for (Path.Node node : path) {
            field = node.getName();
        }
        return field;
    }

    @Error(global = true, status = HttpStatus.UNAUTHORIZED)
    public HttpResponse<ErrorMessage> unauthorizedError(HttpRequest request) {
        ErrorMessage error =
                ErrorMessage.builder()
                        .message(localizationUtils.getMessage(ResponseMessages.UNAUTHORIZED_ERROR))
                        .code(HttpStatus.UNAUTHORIZED.getCode())
                        .self(request.getPath())
                        .build();

        return HttpResponse.<ErrorMessage>unauthorized().body(error); //
    }

    @Error(global = true, status = HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpResponse<ErrorMessage> internalError(HttpRequest request, Throwable exception)
            throws ConstraintViolationException {
        if (exception instanceof ConstraintViolationException) {
            return validationError((ConstraintViolationException) exception, request);
        }
        ErrorMessage error =
                ErrorMessage.builder()
                        .message(localizationUtils.getMessage(ResponseMessages.INTERNAL_ERROR))
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.getCode())
                        .cause(exception == null ? null : exception.getMessage())
                        .self(request.getPath())
                        .build();

        return HttpResponse.<ErrorMessage>serverError().body(error); //
    }
}

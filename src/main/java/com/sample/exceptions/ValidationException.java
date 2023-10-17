/* Mohammed Amine AYACHE (C)2022 */
package com.sample.exceptions;

import java.util.Set;

import com.sample.utils.consts.ExceptionCode;

public class ValidationException extends GenericApplicationException {

    public ValidationException(String message, ExceptionCode exceptionCode) {
        super(message, exceptionCode);
    }

    public ValidationException(String message) {
        this(message, ExceptionCode.BAD_VALIDATION);
    }

    public ValidationException(String message,Set<String> messages){
        this(message,ExceptionCode.BAD_VALIDATION.getCode(),messages);
    }

    public ValidationException(String message, Integer code, Set<String> messages) {
        super(message, code, messages);
    }
}

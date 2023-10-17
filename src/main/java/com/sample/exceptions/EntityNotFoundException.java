/* Mohammed Amine AYACHE (C)2022 */
package com.sample.exceptions;

import java.util.Set;

import com.sample.utils.consts.ExceptionCode;

public class EntityNotFoundException extends GenericApplicationException {
    public EntityNotFoundException(String message, ExceptionCode exceptionCode) {
        super(message, exceptionCode);
    }

    public EntityNotFoundException(String message) {
        this(message, ExceptionCode.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(String message, Integer code, Set<String> messages) {
        super(message, code, messages);
    }
}

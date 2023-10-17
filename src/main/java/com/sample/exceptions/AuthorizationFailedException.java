/* Mohammed Amine AYACHE (C)2022 */
package com.sample.exceptions;

import java.util.Set;

import com.sample.utils.consts.ExceptionCode;

public class AuthorizationFailedException extends GenericApplicationException {
    public AuthorizationFailedException(String message, ExceptionCode exceptionCode) {
        super(message, exceptionCode);
    }

    public AuthorizationFailedException(String message) {
        super(message, ExceptionCode.FORBIDDEN);
    }

    public AuthorizationFailedException() {
        this("forbidden_access");
    }

    public AuthorizationFailedException(String message, Integer code, Set<String> messages) {
        super(message, code, messages);
    }
}

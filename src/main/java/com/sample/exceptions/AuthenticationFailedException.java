/* Mohammed Amine AYACHE (C)2022 */
package com.sample.exceptions;

import java.util.Set;

import com.sample.utils.consts.ExceptionCode;

public class AuthenticationFailedException extends GenericApplicationException {
    public AuthenticationFailedException(String message, ExceptionCode exceptionCode) {
        super(message, exceptionCode);
    }

    public AuthenticationFailedException() {
        this("login_details_error");
    }

    public AuthenticationFailedException(String message) {
        this(message, ExceptionCode.UNAUTHENTICATED);
    }

    public AuthenticationFailedException(String message, Integer code, Set<String> messages) {
        super(message, code, messages);
    }
}

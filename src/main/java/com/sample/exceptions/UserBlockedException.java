/* Mohammed Amine AYACHE (C)2022 */
package com.sample.exceptions;

import java.util.Set;

import com.sample.utils.consts.ExceptionCode;

public class UserBlockedException extends GenericApplicationException {
    public UserBlockedException(String message, ExceptionCode exceptionCode) {
        super(message, exceptionCode);
    }

    public UserBlockedException(String message) {
        super(message);
    }

    public UserBlockedException() {
        this("user_blocked", ExceptionCode.UNAUTHENTICATED);
    }

    public UserBlockedException(String message, Integer code, Set<String> messages) {
        super(message, code, messages);
    }
}

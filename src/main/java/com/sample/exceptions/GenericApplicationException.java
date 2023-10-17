/* Mohammed Amine AYACHE (C)2022 */
package com.sample.exceptions;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.sample.utils.consts.ExceptionCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericApplicationException extends RuntimeException implements Serializable {
    private final Integer code;
    private final Set<String> messages;

    public GenericApplicationException(String message, ExceptionCode exceptionCode) {
        super(message);
        this.code = exceptionCode.getCode();
        this.messages = new HashSet<>();
    }

    public GenericApplicationException() {
        this("Something went wrong");
    }

    public GenericApplicationException(String message) {
        this(message, ExceptionCode.INTERNAL_ERROR);
    }

    public GenericApplicationException(String message, Integer code, Set<String> messages) {
        super(message);
        this.code = code;
        this.messages = messages;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}

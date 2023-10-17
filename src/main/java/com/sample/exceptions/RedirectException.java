package com.sample.exceptions;

import com.sample.utils.consts.ExceptionCode;

public class RedirectException extends GenericApplicationException{
    public RedirectException(String message) {
        super(message, ExceptionCode.ACTION_REQUIRED);
    }
}

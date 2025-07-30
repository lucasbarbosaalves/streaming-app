package com.github.lucasbarbosaalves.catalog.domain.exception;

import com.github.lucasbarbosaalves.catalog.domain.validation.Error;

import java.util.List;

public class InternalErrorException extends NoStackTraceException{

    protected InternalErrorException(final String message, final Throwable t) {
        super(message, t);
    }

    public static InternalErrorException with(final String message, final Throwable t) {
        return new InternalErrorException(message, t);
    }

}

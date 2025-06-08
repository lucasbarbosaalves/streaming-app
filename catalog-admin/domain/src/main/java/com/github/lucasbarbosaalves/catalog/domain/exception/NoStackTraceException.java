package com.github.lucasbarbosaalves.catalog.domain.exception;

public class NoStackTraceException extends RuntimeException {

    public NoStackTraceException(final String message) {
        this(message, null);
    }

    public NoStackTraceException(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }
}

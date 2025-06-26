package com.github.lucasbarbosaalves.catalog.domain.exception;

import com.github.lucasbarbosaalves.catalog.domain.validation.handler.Notification;

public class NotificationException extends DomainException {
    public NotificationException(final String message, final Notification notification) {
        super(message, notification.getErrors());
    }
}

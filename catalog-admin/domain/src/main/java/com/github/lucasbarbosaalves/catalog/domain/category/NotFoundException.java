package com.github.lucasbarbosaalves.catalog.domain.category;

import com.github.lucasbarbosaalves.catalog.domain.AggregateRoot;
import com.github.lucasbarbosaalves.catalog.domain.Identifier;
import com.github.lucasbarbosaalves.catalog.domain.exception.DomainException;
import com.github.lucasbarbosaalves.catalog.domain.validation.Error;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException {

    protected NotFoundException(String message, List<Error> errors) {
        super(message, errors);
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> aggregateClass,
            final Identifier id
    ) {
        final var error = "%s with ID %s was not found".formatted(
                aggregateClass.getSimpleName(),
                id.getValue()
        );
        return new NotFoundException(error, Collections.emptyList());
    }
}

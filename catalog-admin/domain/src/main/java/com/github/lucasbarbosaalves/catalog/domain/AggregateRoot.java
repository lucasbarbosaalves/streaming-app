package com.github.lucasbarbosaalves.catalog.domain;

import com.github.lucasbarbosaalves.catalog.domain.events.DomainEvent;

import java.util.Collections;
import java.util.List;

public abstract class AggregateRoot<ID extends  Identifier> extends Entity<ID> {

    protected AggregateRoot(ID id) {
        super(id, Collections.emptyList());
    }

    protected AggregateRoot(ID id, final List<DomainEvent> domainEvents) {
        super(id, domainEvents);
    }
}

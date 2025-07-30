package com.github.lucasbarbosaalves.catalog.domain;

import com.github.lucasbarbosaalves.catalog.domain.events.DomainEvent;
import com.github.lucasbarbosaalves.catalog.domain.events.DomainEventPublisher;
import com.github.lucasbarbosaalves.catalog.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class Entity<ID extends Identifier> {

    protected final ID id;
    private final List<DomainEvent> domainEvents;

    protected Entity(final ID id, final List<DomainEvent> domainEvents) {
        Objects.requireNonNull(id, "ID should not be null");
        this.id = id;
        this.domainEvents = new ArrayList<>(domainEvents == null ? List.of() : domainEvents);
    }

    public abstract void validate(ValidationHandler handler);

    public ID getId() {
        return id;
    }

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void publishDomainEvents(final DomainEventPublisher publisher) {
        if (publisher == null) {
            return;
        }
        getDomainEvents().forEach(publisher::publishEvent);
        this.domainEvents.clear();
    }

    public void registerEvent(final DomainEvent event) {
        if (event != null) {
            this.domainEvents.add(event);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final Entity<?> entity = (Entity<?>) o;
        return Objects.equals(getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}

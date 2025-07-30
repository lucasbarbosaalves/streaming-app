package com.github.lucasbarbosaalves.catalog.domain;

import com.github.lucasbarbosaalves.catalog.domain.events.DomainEvent;
import com.github.lucasbarbosaalves.catalog.domain.utils.IdUtils;
import com.github.lucasbarbosaalves.catalog.domain.validation.ValidationHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {

    @Test
    public void givenNullEvents_whenInstantiate_shouldOk() {

        // given
        final List<DomainEvent> events = null;
        // when
        DummyEntity entity = new DummyEntity(new DummyID(), events);

        // then
        assertNotNull(entity.getDomainEvents());
        assertTrue(entity.getDomainEvents().isEmpty());
    }

    @Test
    public void givenDomainEvents_whenPassInConstructor_shouldCreateADefensiveClone() {

        // given
        final List<DomainEvent> events = new ArrayList<>();
        events.add((DomainEvent) () -> null);
        // when
        DummyEntity entity = new DummyEntity(new DummyID(), events);

        // then
        assertNotNull(entity.getDomainEvents());
        assertEquals(1, entity.getDomainEvents().size());

        assertThrows(RuntimeException.class, () -> entity.getDomainEvents().add((DomainEvent) () -> null));
    }


    public static class DummyID extends Identifier {
        private final String value;

        public DummyID() {
            this.value = IdUtils.uuid();
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }

    public static class DummyEntity extends Entity<DummyID> {

        public DummyEntity() {
            this(new DummyID(), null);
        }

        protected DummyEntity(DummyID dummyID, List<DomainEvent> domainEvents) {
            super(dummyID, domainEvents);
        }

        @Override
        public void validate(ValidationHandler handler) {

        }
    }

}
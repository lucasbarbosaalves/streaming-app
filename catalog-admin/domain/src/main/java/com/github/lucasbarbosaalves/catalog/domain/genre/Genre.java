package com.github.lucasbarbosaalves.catalog.domain.genre;

import com.github.lucasbarbosaalves.catalog.domain.AggregateRoot;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import com.github.lucasbarbosaalves.catalog.domain.exception.NotificationException;
import com.github.lucasbarbosaalves.catalog.domain.utils.InstantUtils;
import com.github.lucasbarbosaalves.catalog.domain.validation.ValidationHandler;
import com.github.lucasbarbosaalves.catalog.domain.validation.handler.Notification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Genre extends AggregateRoot<GenreID> {

    private String name;
    private boolean active;
    private List<CategoryID> categories;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;


    private Genre(
            final GenreID id,
            final String name,
            final boolean isActive,
            final List<CategoryID> categories,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        super(id);
        this.name = name;
        this.active = isActive;
        this.categories = categories;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;

        selfValidate();
    }


    public static Genre newGenre(final String name, final boolean isActive) {
        final var id = GenreID.unique();
        final var now = InstantUtils.now();
        final var deletedAt = isActive ? null : now;
        return new Genre(id, name, isActive, new ArrayList<>(), now, now, deletedAt);
    }

    public static Genre with(
            final GenreID id,
            final String name,
            final boolean isActive,
            final List<CategoryID> categories,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Genre(id, name, isActive, categories, createdAt, updatedAt, deletedAt);
    }

    //clone constructor
    public static Genre with(final Genre genre) {
        return new Genre(
                genre.getId(),
                genre.name,
                genre.active,
                new ArrayList<>(genre.categories),
                genre.createdAt,
                genre.updatedAt,
                genre.deletedAt
        );
    }

    @Override
    public void validate(ValidationHandler handler) {
        new GenreValidator(this, handler).validate();
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }


    public List<CategoryID> getCategories() {
        return Collections.unmodifiableList(categories);
    }


    public Instant getCreatedAt() {
        return createdAt;
    }


    public Instant getUpdatedAt() {
        return updatedAt;
    }


    public Instant getDeletedAt() {
        return deletedAt;
    }

    public Genre deactivate() {
        if (getDeletedAt() == null) {
            this.deletedAt = InstantUtils.now();
        }
        this.active = false;
        this.updatedAt = InstantUtils.now();
        return this;

    }

    public Genre activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Genre update(final String name, final boolean isActive, final List<CategoryID> categories) {

        if (isActive) {
            activate();
        } else {
            deactivate();
        }
        this.name = name;
        this.categories = new ArrayList<>(categories);
        this.updatedAt = InstantUtils.now();
        selfValidate();
        return this;
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Failed to validate Aggregate Genre", notification);
        }
    }

}

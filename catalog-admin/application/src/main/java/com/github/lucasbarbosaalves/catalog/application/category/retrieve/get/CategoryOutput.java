package com.github.lucasbarbosaalves.catalog.application.category.retrieve.get;

import com.github.lucasbarbosaalves.catalog.domain.category.Category;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;

import java.time.Instant;

public record CategoryOutput(
        CategoryID id,
        String name,
        String description,
        boolean isActive,
        Instant createdAt,
        Instant updatedAt,
        Instant deleteAt
) {

    public static CategoryOutput from(final CategoryID id, final String name, final String description, final boolean isActive, final Instant createdAt, final Instant updatedAt, final Instant deleteAt) {
        return new CategoryOutput(id, name, description, isActive, createdAt, updatedAt, deleteAt);
    }

    public static CategoryOutput from(final Category aCategory) {
        return new CategoryOutput(
                aCategory.getId(),
                aCategory.getName(),
                aCategory.getDescription(),
                aCategory.isActive(),
                aCategory.getCreatedAt(),
                aCategory.getUpdatedAt(),
                aCategory.getDeleteAt()
        );
    }
}

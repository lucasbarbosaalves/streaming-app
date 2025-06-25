package com.github.lucasbarbosaalves.catalog.application.category.retrieve.list;

import com.github.lucasbarbosaalves.catalog.domain.category.Category;

import java.time.Instant;

public record CategoryListOutput(
        String id,
        String name,
        String description,
        boolean isActive,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {

    public static CategoryListOutput from(final Category aCategory) {
        return new CategoryListOutput(
                aCategory.getId().getValue(),
                aCategory.getName(),
                aCategory.getDescription(),
                aCategory.isActive(),
                aCategory.getCreatedAt(),
                aCategory.getUpdatedAt(),
                aCategory.getDeleteAt()
        );
    }
}

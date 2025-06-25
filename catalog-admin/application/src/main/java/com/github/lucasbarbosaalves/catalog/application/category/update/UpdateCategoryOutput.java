package com.github.lucasbarbosaalves.catalog.application.category.update;

import com.github.lucasbarbosaalves.catalog.domain.category.Category;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;

public record UpdateCategoryOutput(
        String id
) {
    public static UpdateCategoryOutput from(final String id) {
        return new UpdateCategoryOutput(id);
    }

    public static UpdateCategoryOutput from(final Category category) {
        return new UpdateCategoryOutput(category.getId().getValue());
    }
}

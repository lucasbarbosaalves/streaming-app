package com.github.lucasbarbosaalves.catalog.application.category.create;

import com.github.lucasbarbosaalves.catalog.domain.category.Category;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;

public record CreateCategoryOutput(
        String id
) {

    public static  CreateCategoryOutput from(final Category category) {
        return new CreateCategoryOutput(category.getId().getValue());
    }
    public static  CreateCategoryOutput from(final String id) {
        return new CreateCategoryOutput(id);
    }

}

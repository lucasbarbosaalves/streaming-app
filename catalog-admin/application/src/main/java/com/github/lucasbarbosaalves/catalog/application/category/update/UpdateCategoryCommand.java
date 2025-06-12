package com.github.lucasbarbosaalves.catalog.application.category.update;

public record UpdateCategoryCommand(
        String id,
        String name,
        String description,
        boolean isActive
) {

    public static UpdateCategoryCommand with(String id, String name, String description, boolean isActive) {
        return new UpdateCategoryCommand(id, name, description, isActive);
    }


}

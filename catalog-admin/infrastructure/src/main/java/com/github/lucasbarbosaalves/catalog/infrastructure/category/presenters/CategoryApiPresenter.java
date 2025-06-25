package com.github.lucasbarbosaalves.catalog.infrastructure.category.presenters;

import com.github.lucasbarbosaalves.catalog.application.category.retrieve.get.CategoryOutput;
import com.github.lucasbarbosaalves.catalog.application.category.retrieve.list.CategoryListOutput;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.dto.CategoryResponse;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.dto.CategoryListResponse;

public interface CategoryApiPresenter {

    static CategoryResponse present(final CategoryOutput categoryOutput) {
        return new CategoryResponse(
                categoryOutput.id().getValue(),
                categoryOutput.name(),
                categoryOutput.description(),
                categoryOutput.isActive(),
                categoryOutput.createdAt(),
                categoryOutput.updatedAt(),
                categoryOutput.deleteAt()
        );
    }


    static CategoryListResponse present(final CategoryListOutput categoryListOutput) {
        return new CategoryListResponse(
                categoryListOutput.id(),
                categoryListOutput.name(),
                categoryListOutput.description(),
                categoryListOutput.isActive(),
                categoryListOutput.createdAt(),
                categoryListOutput.deletedAt()
        );
    }
}

package com.github.lucasbarbosaalves.catalog.application.category.retrieve.get;

import com.github.lucasbarbosaalves.catalog.domain.category.CategoryGateway;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import com.github.lucasbarbosaalves.catalog.domain.exception.DomainException;
import com.github.lucasbarbosaalves.catalog.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase{

    private final CategoryGateway categoryGateway;

    public DefaultGetCategoryByIdUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CategoryOutput execute(final String id) {
        final var aCategoryId = CategoryID.from(id);

        return this.categoryGateway.findById(aCategoryId)
                .map(category -> CategoryOutput.from(
                        category.getId(),
                        category.getName(),
                        category.getDescription(),
                        category.isActive(),
                        category.getCreatedAt(),
                        category.getUpdatedAt(),
                        category.getDeleteAt()
                ))
                .orElseThrow(notFound(aCategoryId));
    }

    private Supplier<DomainException> notFound(final CategoryID id) {
        return () -> DomainException.with(new Error("Category with ID %s was not found".formatted(id.getValue())));
    }
}

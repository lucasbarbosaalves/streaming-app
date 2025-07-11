package com.github.lucasbarbosaalves.catalog.application.category.retrieve.list;

import com.github.lucasbarbosaalves.catalog.domain.category.CategoryGateway;
import com.github.lucasbarbosaalves.catalog.domain.pagination.SearchQuery;
import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase {
    
    private final CategoryGateway categoryGateway;

    public DefaultListCategoriesUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(final SearchQuery query) {
        return this.categoryGateway.findAll(query)
                .map(CategoryListOutput::from);
    }
}

package com.github.lucasbarbosaalves.catalog.infrastructure.category;

import com.github.lucasbarbosaalves.catalog.domain.category.Category;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryGateway;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import com.github.lucasbarbosaalves.catalog.domain.category.CategorySearchQuery;
import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryMySQLGateway implements CategoryGateway {

    private final CategoryRepository categoryRepository;

    public CategoryMySQLGateway(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(Category category) {
        return null;
    }

    @Override
    public void deleteById(CategoryID categoryID) {

    }

    @Override
    public Optional<Category> findById(CategoryID categoryID) {
        return Optional.empty();
    }

    @Override
    public Category update(Category category) {
        return null;
    }

    @Override
    public Pagination<Category> findAll(CategorySearchQuery query) {
        return null;
    }
}

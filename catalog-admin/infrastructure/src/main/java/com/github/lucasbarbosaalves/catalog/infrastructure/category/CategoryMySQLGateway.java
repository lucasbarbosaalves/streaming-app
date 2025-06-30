package com.github.lucasbarbosaalves.catalog.infrastructure.category;

import com.github.lucasbarbosaalves.catalog.domain.category.Category;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryGateway;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import com.github.lucasbarbosaalves.catalog.domain.pagination.SearchQuery;
import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence.CategoryJpaEntity;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence.CategoryRepository;
import com.github.lucasbarbosaalves.catalog.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.github.lucasbarbosaalves.catalog.infrastructure.utils.SpecificationUtils.like;
import static org.springframework.data.domain.Sort.*;

@Service
public class CategoryMySQLGateway implements CategoryGateway {

    private final CategoryRepository categoryRepository;

    public CategoryMySQLGateway(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(final Category category) {
        return save(category);
    }

    @Override
    public void deleteById(final CategoryID categoryID) {
        final String id = categoryID.getValue();

        if (this.categoryRepository.existsById(id)) {
            this.categoryRepository.deleteById(id);
        }
    }

    @Override
    public Optional<Category> findById(final CategoryID categoryID) {
        return this.categoryRepository.findById(categoryID.getValue())
                .map(CategoryJpaEntity::toAgreggate);
    }

    @Override
    public Category update(final Category category) {
        return save(category);
    }

    @Override
    public Pagination<Category> findAll(final SearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                by(Direction.fromString(query.direction()), query.sort()));

        final var specifications = Optional.ofNullable(query.terms())
                .filter(str -> !str.isBlank())
                .map(str -> SpecificationUtils
                        .<CategoryJpaEntity>like("name", str)
                        .or(like("description", str))
                )
                .orElse(null);

        final var pageResult = this.categoryRepository.findAll(Specification.where(specifications), page);
        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CategoryJpaEntity::toAgreggate).toList()
        );
    }

    @Override
    public List<CategoryID> existsByIds(Iterable<CategoryID> id) {
        // TODO: Implementar após criar camada de infra
        return Collections.emptyList();
    }

    private Category save(final Category category) {
        return categoryRepository.save(CategoryJpaEntity.from(category)).toAgreggate();
    }
}

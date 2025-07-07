package com.github.lucasbarbosaalves.catalog.infrastructure.category;

import com.github.lucasbarbosaalves.catalog.domain.category.Category;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryGateway;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;
import com.github.lucasbarbosaalves.catalog.domain.pagination.SearchQuery;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence.CategoryJpaEntity;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence.CategoryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.github.lucasbarbosaalves.catalog.infrastructure.utils.SpecificationUtils.like;
import static org.springframework.data.domain.Sort.Direction;

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
    public Pagination<Category> findAll(final SearchQuery aQuery) {
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var specifications = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var pageResult =
                this.categoryRepository.findAll(Specification.where(specifications), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CategoryJpaEntity::toAgreggate).toList()
        );
    }

    @Override
    public List<CategoryID> existsByIds(final Iterable<CategoryID> categoryIDs) {
        final var ids = StreamSupport.stream(categoryIDs.spliterator(), false)
                .map(CategoryID::getValue)
                .toList();
        return this.categoryRepository.existsByIds(ids).stream()
                .map(CategoryID::from)
                .toList();
    }

    private Category save(final Category category) {
        return categoryRepository.save(CategoryJpaEntity.from(category)).toAgreggate();
    }

    private Specification<CategoryJpaEntity> assembleSpecification(final String str) {
        final Specification<CategoryJpaEntity> nameLike = like("name", str);
        final Specification<CategoryJpaEntity> descriptionLike = like("description", str);
        return nameLike.or(descriptionLike);
    }
}

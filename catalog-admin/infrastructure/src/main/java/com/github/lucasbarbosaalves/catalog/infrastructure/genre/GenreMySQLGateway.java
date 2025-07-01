package com.github.lucasbarbosaalves.catalog.infrastructure.genre;

import com.github.lucasbarbosaalves.catalog.domain.genre.Genre;
import com.github.lucasbarbosaalves.catalog.domain.genre.GenreGateway;
import com.github.lucasbarbosaalves.catalog.domain.genre.GenreID;
import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;
import com.github.lucasbarbosaalves.catalog.domain.pagination.SearchQuery;
import com.github.lucasbarbosaalves.catalog.infrastructure.genre.persistence.GenreJpaEntity;
import com.github.lucasbarbosaalves.catalog.infrastructure.genre.persistence.GenreRepository;
import com.github.lucasbarbosaalves.catalog.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class GenreMySQLGateway implements GenreGateway {

    private final GenreRepository genreRepository;

    public GenreMySQLGateway(GenreRepository genreRepository) {
        this.genreRepository = Objects.requireNonNull(genreRepository);
    }

    @Override
    public Genre create(Genre genre) {
        return save(genre);
    }

    @Override
    public void deleteById(final GenreID id) {
        final var genreID = id.getValue();
        if (this.genreRepository.existsById(genreID)) {
            this.genreRepository.deleteById(genreID);
        }
    }

    @Override
    public Optional<Genre> findById(GenreID id) {
        return this.genreRepository.findById(id.getValue())
                .map(GenreJpaEntity::toAgreggate);
    }

    @Override
    public Genre update(Genre genre) {
        return save(genre);
    }

    @Override
    public Pagination<Genre> findAll(final SearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort()));
        final var where = Optional.ofNullable(query.terms())
                .filter(str -> !str.isBlank())
                .map(this::assmbleSpecification)
                .orElse(null);

        final var result = this.genreRepository.findAll(Specification.where(where), page);
        return new Pagination<>(
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.map(GenreJpaEntity::toAgreggate).toList()
        );
    }

    private Specification<GenreJpaEntity> assmbleSpecification(final String terms) {
        return SpecificationUtils.like("name", terms);
    }

    private Genre save(final Genre genre) {
        return this.genreRepository.save(GenreJpaEntity.from(genre))
                .toAgreggate();
    }
}

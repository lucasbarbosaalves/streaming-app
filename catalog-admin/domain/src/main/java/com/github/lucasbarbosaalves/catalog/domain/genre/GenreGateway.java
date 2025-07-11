package com.github.lucasbarbosaalves.catalog.domain.genre;

import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;
import com.github.lucasbarbosaalves.catalog.domain.pagination.SearchQuery;

import java.util.Optional;

public interface GenreGateway {

    Genre create(Genre genre);

    void deleteById(GenreID id);

    Optional<Genre> findById(GenreID id);

    Genre update(Genre genre);

    Pagination<Genre> findAll(SearchQuery query);
}

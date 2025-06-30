package com.github.lucasbarbosaalves.catalog.application.genre.retrieve.list;

import com.github.lucasbarbosaalves.catalog.domain.genre.GenreGateway;
import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;
import com.github.lucasbarbosaalves.catalog.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListGenreUseCase extends  ListGenreUseCase {

   private final GenreGateway genreGateway;

    public DefaultListGenreUseCase(GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public Pagination<GenreListOutput> execute(final SearchQuery query) {
        return this.genreGateway.findAll(query)
                .map(GenreListOutput::from);
    }
}

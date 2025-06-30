package com.github.lucasbarbosaalves.catalog.application.genre.delete;

import com.github.lucasbarbosaalves.catalog.domain.genre.GenreGateway;
import com.github.lucasbarbosaalves.catalog.domain.genre.GenreID;

import java.util.Objects;

public class DefaultDeleteGenreUseCase extends DeleteGenreUseCase{

    private final GenreGateway genreGateway;

    public DefaultDeleteGenreUseCase(GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public void execute(final String id) {
        this.genreGateway.deleteById(GenreID.from(id));
    }
}

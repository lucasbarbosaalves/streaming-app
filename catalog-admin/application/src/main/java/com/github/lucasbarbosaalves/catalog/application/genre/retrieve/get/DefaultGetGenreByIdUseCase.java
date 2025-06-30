package com.github.lucasbarbosaalves.catalog.application.genre.retrieve.get;

import com.github.lucasbarbosaalves.catalog.domain.category.NotFoundException;
import com.github.lucasbarbosaalves.catalog.domain.genre.Genre;
import com.github.lucasbarbosaalves.catalog.domain.genre.GenreGateway;
import com.github.lucasbarbosaalves.catalog.domain.genre.GenreID;

import java.util.Objects;

public class DefaultGetGenreByIdUseCase extends GetGenreByIdUseCase {

    private final GenreGateway genreGateway;

    public DefaultGetGenreByIdUseCase(GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public GenreOutput execute(final String id) {

        final var genreId = GenreID.from(id);

        return this.genreGateway.findById(GenreID.from(id))
                .map(GenreOutput::from)
                .orElseThrow(() -> NotFoundException.with(Genre.class, genreId));
    }
}

package com.github.lucasbarbosaalves.catalog.application.genre.create;

import com.github.lucasbarbosaalves.catalog.domain.genre.Genre;

public record CreateGenreOutput(String id) {

    public static CreateGenreOutput from(final Genre genre) {
        return new CreateGenreOutput(genre.getId().getValue());
    }

    public static CreateGenreOutput from(final String id) {
        return new CreateGenreOutput(id);
    }
}

package com.github.lucasbarbosaalves.catalog.application.genre.update;

import com.github.lucasbarbosaalves.catalog.domain.genre.Genre;

public record UpdateGenreOutput(String id) {

    public static UpdateGenreOutput from(final Genre genre) {
        return new UpdateGenreOutput(genre.getId().getValue());
    }
}

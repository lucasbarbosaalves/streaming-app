package com.github.lucasbarbosaalves.catalog.infrastructure.genre.presenters;

import com.github.lucasbarbosaalves.catalog.application.genre.retrieve.get.GenreOutput;
import com.github.lucasbarbosaalves.catalog.application.genre.retrieve.list.GenreListOutput;
import com.github.lucasbarbosaalves.catalog.infrastructure.genre.models.GenreListResponse;
import com.github.lucasbarbosaalves.catalog.infrastructure.genre.models.GenreResponse;

public interface GenreApiPresenter {

    static GenreResponse present(final GenreOutput output) {
        return new GenreResponse(
                output.id(),
                output.name(),
                output.categories(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }


    static GenreListResponse present(final GenreListOutput output) {
        return new GenreListResponse(
                output.id(),
                output.name(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}

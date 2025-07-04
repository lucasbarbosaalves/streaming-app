package com.github.lucasbarbosaalves.catalog.infrastructure.api.controllers;

import com.github.lucasbarbosaalves.catalog.application.genre.create.CreateGenreCommand;
import com.github.lucasbarbosaalves.catalog.application.genre.create.CreateGenreOutput;
import com.github.lucasbarbosaalves.catalog.application.genre.create.CreateGenreUseCase;
import com.github.lucasbarbosaalves.catalog.application.genre.delete.DeleteGenreUseCase;
import com.github.lucasbarbosaalves.catalog.application.genre.retrieve.get.GetGenreByIdUseCase;
import com.github.lucasbarbosaalves.catalog.application.genre.retrieve.list.ListGenreUseCase;
import com.github.lucasbarbosaalves.catalog.application.genre.update.UpdateGenreCommand;
import com.github.lucasbarbosaalves.catalog.application.genre.update.UpdateGenreOutput;
import com.github.lucasbarbosaalves.catalog.application.genre.update.UpdateGenreUseCase;
import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;
import com.github.lucasbarbosaalves.catalog.domain.pagination.SearchQuery;
import com.github.lucasbarbosaalves.catalog.infrastructure.api.GenreAPI;
import com.github.lucasbarbosaalves.catalog.infrastructure.genre.models.CreateGenreRequest;
import com.github.lucasbarbosaalves.catalog.infrastructure.genre.models.GenreListResponse;
import com.github.lucasbarbosaalves.catalog.infrastructure.genre.models.GenreResponse;
import com.github.lucasbarbosaalves.catalog.infrastructure.genre.presenters.GenreApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class GenreController implements GenreAPI {

    private final CreateGenreUseCase createGenreUseCase;

    private final GetGenreByIdUseCase getGenreByIdUseCase;

    private final ListGenreUseCase listGenreUseCase;

    private final UpdateGenreUseCase updateGenreUseCase;

    private final DeleteGenreUseCase deleteGenreUseCase;

    public GenreController(CreateGenreUseCase createGenreUseCase, GetGenreByIdUseCase getGenreByIdUseCase, ListGenreUseCase listGenreUseCase, UpdateGenreUseCase updateGenreUseCase, DeleteGenreUseCase deleteGenreUseCase) {
        this.createGenreUseCase = createGenreUseCase;
        this.getGenreByIdUseCase = getGenreByIdUseCase;
        this.listGenreUseCase = listGenreUseCase;
        this.updateGenreUseCase = updateGenreUseCase;
        this.deleteGenreUseCase = deleteGenreUseCase;
    }

    @Override
    public ResponseEntity<?> create(CreateGenreRequest input) {
        final var command = CreateGenreCommand.with(
                input.name(),
                input.isActive(),
                input.categories()
        );

        CreateGenreOutput output = this.createGenreUseCase.execute(command);

        return ResponseEntity.created(URI.create("/genres/" + output.id())).body(output);
    }

    @Override
    public Pagination<GenreListResponse> list(String search, int page, int perPage, String sort, String direction) {
        final var query = this.listGenreUseCase.execute(
                new SearchQuery(
                        page,
                        perPage,
                        search,
                        sort,
                        direction
                )
        );

        return query.map(GenreApiPresenter::present);
    }

    @Override
    public GenreResponse getById(String id) {
        return GenreApiPresenter.present(this.getGenreByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(String id, CreateGenreRequest input) {
        final var command = UpdateGenreCommand.with(
                id,
                input.name(),
                input.isActive(),
                input.categories()
        );

        UpdateGenreOutput output = this.updateGenreUseCase.execute(command);

        return ResponseEntity.ok(output);
    }

    @Override
    public void deleteById(String id) {
        this.deleteGenreUseCase.execute(id);
    }
}

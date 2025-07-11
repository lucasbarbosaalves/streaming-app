package com.github.lucasbarbosaalves.catalog.application.genre.retrieve.list;

import com.github.lucasbarbosaalves.catalog.application.UseCase;
import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;
import com.github.lucasbarbosaalves.catalog.domain.pagination.SearchQuery;

public abstract class ListGenreUseCase extends UseCase<SearchQuery, Pagination<GenreListOutput>> {
}

package com.github.lucasbarbosaalves.catalog.infrastructure.api.controllers;

import com.github.lucasbarbosaalves.catalog.application.category.delete.DeleteCategoryUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.retrieve.list.ListCategoriesUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.update.UpdateCategoryCommand;
import com.github.lucasbarbosaalves.catalog.application.category.update.UpdateCategoryOutput;
import com.github.lucasbarbosaalves.catalog.application.category.update.UpdateCategoryUseCase;
import com.github.lucasbarbosaalves.catalog.domain.pagination.SearchQuery;
import com.github.lucasbarbosaalves.catalog.infrastructure.api.CategoryAPI;
import com.github.lucasbarbosaalves.catalog.application.category.create.CreateCategoryCommand;
import com.github.lucasbarbosaalves.catalog.application.category.create.CreateCategoryOutput;
import com.github.lucasbarbosaalves.catalog.application.category.create.CreateCategoryUseCase;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.dto.CategoryListResponse;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.dto.CategoryResponse;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.dto.CreateCategoryRequest;
import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;
import com.github.lucasbarbosaalves.catalog.domain.validation.handler.Notification;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.dto.UpdateCategoryRequest;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.presenters.CategoryApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;

    private final GetCategoryByIdUseCase getCategoryByIdUseCase;

    private final UpdateCategoryUseCase updateCategoryUseCase;

    private final DeleteCategoryUseCase deleteCategoryUseCase;

    private final ListCategoriesUseCase listCategoriesUseCase;

    public CategoryController(CreateCategoryUseCase createCategoryUseCase, GetCategoryByIdUseCase getCategoryByIdUseCase, UpdateCategoryUseCase updateCategoryUseCase, DeleteCategoryUseCase deleteCategoryUseCase, ListCategoriesUseCase listCategoriesUseCase) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.getCategoryByIdUseCase = getCategoryByIdUseCase;
        this.updateCategoryUseCase = updateCategoryUseCase;
        this.deleteCategoryUseCase = deleteCategoryUseCase;
        this.listCategoriesUseCase = listCategoriesUseCase;
    }

    @Override
    public ResponseEntity<?> createCategory(final CreateCategoryRequest input) {
        final var aCommand = CreateCategoryCommand.with(
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/categories/" + output.id())).body(output);

        return this.createCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<CategoryListResponse> listCategories(String search, int page, int perPage, String sort, String direction) {
        return this.listCategoriesUseCase.execute(new SearchQuery(
                page,
                perPage,
                search,
                sort,
                direction
        )).map(CategoryApiPresenter::present);
                
    }

    @Override
    public CategoryResponse getById(String id) {
        return CategoryApiPresenter.present(this.getCategoryByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateCategoryRequest input) {
        final var aCommand = UpdateCategoryCommand.with(
                id,
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<UpdateCategoryOutput, ResponseEntity<?>> onSuccess =
                ResponseEntity::ok;

        return this.updateCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public void deleteById(final String id) {
        this.deleteCategoryUseCase.execute(id);
    }
}

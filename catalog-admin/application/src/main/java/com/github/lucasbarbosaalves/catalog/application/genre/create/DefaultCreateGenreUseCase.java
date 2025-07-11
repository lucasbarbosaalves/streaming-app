package com.github.lucasbarbosaalves.catalog.application.genre.create;

import com.github.lucasbarbosaalves.catalog.domain.category.CategoryGateway;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import com.github.lucasbarbosaalves.catalog.domain.exception.NotificationException;
import com.github.lucasbarbosaalves.catalog.domain.genre.Genre;
import com.github.lucasbarbosaalves.catalog.domain.genre.GenreGateway;
import com.github.lucasbarbosaalves.catalog.domain.validation.Error;
import com.github.lucasbarbosaalves.catalog.domain.validation.ValidationHandler;
import com.github.lucasbarbosaalves.catalog.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultCreateGenreUseCase extends CreateGenreUseCase {

    private final CategoryGateway categoryGateway;

    private final GenreGateway genreGateway;

    public DefaultCreateGenreUseCase(final CategoryGateway categoryGateway, final GenreGateway genreGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
        ;
    }

    @Override
    public CreateGenreOutput execute(final CreateGenreCommand command) {
        final var name = command.name();
        final var isActive = command.isActive();
        final var categories = toCategoryID(command.categories());

        final var notification = Notification.create();
        notification.append(validateCategories(categories));
        final var aGenre = notification.validate(() -> Genre.newGenre(name, isActive));

        if (notification.hasError()) {
            throw new NotificationException("Could not create Aggregate Genre", notification);
        }
        aGenre.addCategories(categories);
        return CreateGenreOutput.from(this.genreGateway.create(aGenre));
    }

    private ValidationHandler validateCategories(final List<CategoryID> ids) {
        final var notification = Notification.create();
        if (ids.isEmpty() || ids == null) {
            return notification;
        }
        final var retrieveIds = categoryGateway.existsByIds(ids);
        if (ids.size() != retrieveIds.size()) {
            final var commandIds = new ArrayList<>(ids);  // create a mutable copy
            commandIds.removeAll(retrieveIds);

            final var missingIds = commandIds.stream()
                    .map(CategoryID::getValue)
                    .collect(Collectors.joining(", "));

            notification.append(new Error("Some categories could not be found: " + missingIds));
        }
        return notification;
    }

    private List<CategoryID> toCategoryID(final List<String> categories) {
        return categories.stream()
                .map(CategoryID::from)
                .toList();
    }
}

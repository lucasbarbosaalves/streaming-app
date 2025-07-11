package com.github.lucasbarbosaalves.catalog.application.genre.update;

import com.github.lucasbarbosaalves.catalog.application.genre.create.CreateGenreOutput;
import com.github.lucasbarbosaalves.catalog.domain.Identifier;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryGateway;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import com.github.lucasbarbosaalves.catalog.domain.category.NotFoundException;
import com.github.lucasbarbosaalves.catalog.domain.exception.DomainException;
import com.github.lucasbarbosaalves.catalog.domain.exception.NotificationException;
import com.github.lucasbarbosaalves.catalog.domain.genre.Genre;
import com.github.lucasbarbosaalves.catalog.domain.genre.GenreGateway;
import com.github.lucasbarbosaalves.catalog.domain.genre.GenreID;
import com.github.lucasbarbosaalves.catalog.domain.validation.Error;
import com.github.lucasbarbosaalves.catalog.domain.validation.ValidationHandler;
import com.github.lucasbarbosaalves.catalog.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DefaultUpdateGenreUseCase extends UpdateGenreUseCase{

    private final CategoryGateway categoryGateway;

    private final GenreGateway genreGateway;

    public DefaultUpdateGenreUseCase(final CategoryGateway categoryGateway, final GenreGateway genreGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }


    @Override
    public UpdateGenreOutput execute(UpdateGenreCommand command) {
        final var id = GenreID.from(command.id());
        final var name = command.name();
        final var isActive = command.isActive();
        final var categories = toCategoryID(command.categories());

        final var genre = this.genreGateway.findById(id)
                .orElseThrow(notFound(id));

        final var notification = Notification.create();
        notification.append(validateCategories(categories));
        notification.validate(() -> genre.update(name, isActive, categories));

        if (notification.hasError()) {
            throw new NotificationException("Could not update Aggregate Genre", notification);
        }
        return UpdateGenreOutput.from(this.genreGateway.update(genre));
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

    private Supplier<DomainException> notFound(final Identifier id) {
        return () -> NotFoundException.with(Genre.class, id);
    }

    private List<CategoryID> toCategoryID(List<String> categories) {
        return categories.stream()
                .map(CategoryID::from)
                .toList();
    }
}

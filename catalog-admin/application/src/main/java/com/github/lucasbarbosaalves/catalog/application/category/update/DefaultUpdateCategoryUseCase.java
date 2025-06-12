package com.github.lucasbarbosaalves.catalog.application.category.update;

import com.github.lucasbarbosaalves.catalog.domain.category.Category;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryGateway;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import com.github.lucasbarbosaalves.catalog.domain.exception.DomainException;
import com.github.lucasbarbosaalves.catalog.domain.validation.Error;
import com.github.lucasbarbosaalves.catalog.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

import static io.vavr.API.*;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase{

    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(UpdateCategoryCommand aCommand) {
        final var id = CategoryID.from(aCommand.id());
        final String name = aCommand.name();
        final String description = aCommand.description();
        final boolean isActive = aCommand.isActive();

        final var aCategory = this.categoryGateway.findById(id)
                .orElseThrow(notFound(id));

        final var notification = Notification.create();

        aCategory.update(name, description, isActive).validate(notification);

        return notification.hasError()
                ? Either.left(notification)
                : update(aCategory);
    }

    private Either<Notification, UpdateCategoryOutput> update(final Category aCategory) {
        return Try(()-> this.categoryGateway.update(aCategory))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);
    }

    private Supplier<DomainException> notFound(final CategoryID id) {
        return () -> DomainException.with(new Error("Category with ID %s was not found".formatted(id.getValue())));
    }
}

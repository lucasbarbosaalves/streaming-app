package com.github.lucasbarbosaalves.catalog.application.category.update;

import com.github.lucasbarbosaalves.catalog.application.UseCase;
import com.github.lucasbarbosaalves.catalog.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {
}

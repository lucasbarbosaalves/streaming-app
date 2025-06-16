package com.github.lucasbarbosaalves.catalog.application.category.delete;

import com.github.lucasbarbosaalves.catalog.application.UnitUseCase;

public abstract class DeleteCategoryUseCase extends UnitUseCase<String> {

    public abstract void execute(String id);
}

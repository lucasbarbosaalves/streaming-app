package com.github.lucasbarbosaalves.catalog.application.category.retrieve.list;

import com.github.lucasbarbosaalves.catalog.application.UseCase;
import com.github.lucasbarbosaalves.catalog.domain.category.CategorySearchQuery;
import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {

}

package com.github.lucasbarbosaalves.catalog.infrastructure.config.usecases;

import com.github.lucasbarbosaalves.catalog.application.category.create.CreateCategoryUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.create.DefaultCreateCategoryUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.delete.DefaultDeleteCategoryUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.delete.DeleteCategoryUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.retrieve.list.DefaultListCategoriesUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.retrieve.list.ListCategoriesUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.update.DefaultUpdateCategoryUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.update.UpdateCategoryUseCase;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {

    private final CategoryGateway categoryGateway;

    public CategoryUseCaseConfig(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(categoryGateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase(final CategoryGateway categoryGateway) {
        return new DefaultUpdateCategoryUseCase(categoryGateway);
    }


    @Bean
    public GetCategoryByIdUseCase getCategoryByIdUseCase(final CategoryGateway categoryGateway) {
        return new DefaultGetCategoryByIdUseCase(categoryGateway);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase(final CategoryGateway categoryGateway) {
        return new DefaultListCategoriesUseCase(categoryGateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase(final CategoryGateway categoryGateway) {
        return new DefaultDeleteCategoryUseCase(categoryGateway);
    }


}

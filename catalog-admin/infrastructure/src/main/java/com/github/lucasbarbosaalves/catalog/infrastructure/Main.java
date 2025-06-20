package com.github.lucasbarbosaalves.catalog.infrastructure;

import com.github.lucasbarbosaalves.catalog.application.category.create.CreateCategoryUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.delete.DeleteCategoryUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.retrieve.list.ListCategoriesUseCase;
import com.github.lucasbarbosaalves.catalog.application.category.update.UpdateCategoryUseCase;
import com.github.lucasbarbosaalves.catalog.domain.category.Category;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence.CategoryJpaEntity;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence.CategoryRepository;
import com.github.lucasbarbosaalves.catalog.infrastructure.config.WebServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.AbstractEnvironment;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
      System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "development");
//        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "development");
        SpringApplication.run(WebServerConfig.class, args);
    }
}
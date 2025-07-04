package com.github.lucasbarbosaalves.catalog;

import com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence.CategoryRepository;
import com.github.lucasbarbosaalves.catalog.infrastructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.List;

public class CleanUpExtensions implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        final var applicationContext = SpringExtension.getApplicationContext(context);

        cleanUp(List.of(
                applicationContext.getBean(GenreRepository.class),
                applicationContext.getBean(CategoryRepository.class)
        ));

    }

    private void cleanUp(final Collection<CrudRepository> repositories) {
        repositories.forEach(CrudRepository::deleteAll);
    }
}
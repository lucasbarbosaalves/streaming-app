package com.github.lucasbarbosaalves.catalog;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

class CleanUpExtensions implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        var repositories = SpringExtension.getApplicationContext(context).getBeansOfType(CrudRepository.class).values();

        cleanUp(repositories);
    }

    private void cleanUp(final Collection<CrudRepository> repositories) {
        repositories.forEach(CrudRepository::deleteAll);
    }
}
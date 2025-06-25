package com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence;

import com.github.lucasbarbosaalves.catalog.MySQLGatewayTest;
import com.github.lucasbarbosaalves.catalog.domain.category.Category;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MySQLGatewayTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void givenAnInvalidNullName_whenCallsSave_thenShouldReturnError() {
        final var aCategory = Category.newCategory("Filmes", "All about movies", true);
        final var anEntity = CategoryJpaEntity.from(aCategory);
        anEntity.setName(null); // Define o campo inválido

        final var actualException = assertThrows(
                DataIntegrityViolationException.class,
                () -> categoryRepository.saveAndFlush(anEntity)
        );

        assertInstanceOf(
                ConstraintViolationException.class,
                actualException.getCause()
        );
    }

    @Test
    public void givenAnInvalidNullCreatedAt_whenCallsSave_thenShouldReturnError() {
        final var aCategory = Category.newCategory("Filmes", "All about movies", true);
        final var anEntity = CategoryJpaEntity.from(aCategory);
        anEntity.setCreatedAt(null); // Define o campo inválido

        final var actualException = assertThrows(
                DataIntegrityViolationException.class,
                () -> categoryRepository.saveAndFlush(anEntity)
        );

        assertInstanceOf(
                ConstraintViolationException.class,
                actualException.getCause()
        );
    }

    @Test
    public void givenAnInvalidNullUpdatedAt_whenCallsSave_thenShouldReturnError() {
        final var aCategory = Category.newCategory("Filmes", "All about movies", true);
        final var anEntity = CategoryJpaEntity.from(aCategory);
        anEntity.setUpdatedAt(null); // Define o campo inválido

        final var actualException = assertThrows(
                DataIntegrityViolationException.class,
                () -> categoryRepository.saveAndFlush(anEntity)
        );

        assertInstanceOf(
                ConstraintViolationException.class,
                actualException.getCause()
        );
    }
}
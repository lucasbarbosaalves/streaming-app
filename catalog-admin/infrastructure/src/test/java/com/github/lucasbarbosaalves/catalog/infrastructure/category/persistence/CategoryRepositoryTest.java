
package com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence;

import com.github.lucasbarbosaalves.catalog.domain.category.Category;
import com.github.lucasbarbosaalves.catalog.infrastructure.MySQLGatewayTest;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@MySQLGatewayTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void givenAnInvalidNullName_whenCallsSave_thenShouldReturnError() {
        final var expectedErrorMessage = "not-null property references a null or transient value: com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence.CategoryJpaEntity.name";
        final var expectedPropertyName = "name";
        final var aCategory = Category.newCategory("Filmes", "All about movies", true);

        final var anEntity = CategoryJpaEntity.from(aCategory);
        anEntity.setName(null);

        final var actualException = Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(anEntity));

        final var actualCause = Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());

        Assertions.assertEquals(expectedPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedErrorMessage, actualCause.getMessage());
    }

    @Test
    public void givenAnInvalidNullCreatedAt_whenCallsSave_thenShouldReturnError() {
        final var expectedErrorMessage = "not-null property references a null or transient value: com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence.CategoryJpaEntity.createdAt";
        final var expectedPropertyName = "createdAt";
        final var aCategory = Category.newCategory("Filmes", "All about movies", true);

        final var anEntity = CategoryJpaEntity.from(aCategory);
        anEntity.setCreatedAt(null);

        final var actualException = Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(anEntity));

        final var actualCause = Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());

        Assertions.assertEquals(expectedPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedErrorMessage, actualCause.getMessage());
    }


    @Test
    public void givenAnInvalidNullUpdatedAt_whenCallsSave_thenShouldReturnError() {
        final var expectedErrorMessage = "not-null property references a null or transient value: com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence.CategoryJpaEntity.updatedAt";
        final var expectedPropertyName = "updatedAt";
        final var aCategory = Category.newCategory("Filmes", "All about movies", true);

        final var anEntity = CategoryJpaEntity.from(aCategory);
        anEntity.setUpdatedAt(null);

        final var actualException = Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(anEntity));

        final var actualCause = Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());

        Assertions.assertEquals(expectedPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedErrorMessage, actualCause.getMessage());
    }
}

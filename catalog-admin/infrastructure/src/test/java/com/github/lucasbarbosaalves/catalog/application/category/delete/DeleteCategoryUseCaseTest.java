package com.github.lucasbarbosaalves.catalog.application.category.delete;

import com.github.lucasbarbosaalves.catalog.IntegrationTest;
import com.github.lucasbarbosaalves.catalog.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.github.lucasbarbosaalves.catalog.domain.category.Category;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryGateway;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence.CategoryJpaEntity;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

@IntegrationTest
public class DeleteCategoryUseCaseTest {

    @Autowired
    private DeleteCategoryUseCase useCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @MockitoSpyBean
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldOk() {
        final var aCategory = Category.newCategory("Filmes", "Ação", true);
        final var expectedId = aCategory.getId();

        save(aCategory);

        assertEquals(1, categoryRepository.count());
        
        assertDoesNotThrow(() ->useCase.execute(expectedId.getValue()));

        assertEquals(0, categoryRepository.count());

    }

    @Test
    public void givenAnInvalidId_whenCallsDeleteCategory_shouldOk() {
        final var expectedId = CategoryID.from("123");

        assertEquals(0, categoryRepository.count());

        assertDoesNotThrow(() ->useCase.execute(expectedId.getValue()));
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.getId();

        doThrow(new IllegalStateException("Gateway error"))
                .when(categoryGateway).deleteById(eq(expectedId));

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));
    }

    private void save(final Category... aCategory) {
        categoryRepository.saveAllAndFlush(Arrays.stream(aCategory)
                .map(CategoryJpaEntity::from)
                .toList()
        );
    }
}

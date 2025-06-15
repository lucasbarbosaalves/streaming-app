package com.github.lucasbarbosaalves.catalog.application.category.delete;

import com.github.lucasbarbosaalves.catalog.domain.category.Category;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryGateway;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTest {

    @InjectMocks
    private DefaultDeleteCategoryUseCase deleteCategoryUseCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        reset(categoryGateway);
    }
    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldOk() {
        final var aCategory = Category.newCategory("Filmes", "Ação", true);
        final var expectedId = aCategory.getId();

        Mockito.doNothing()
                .when(categoryGateway).deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() ->deleteCategoryUseCase.execute(expectedId.getValue()));
        Mockito.verify(categoryGateway, Mockito.times(1)).deleteById(eq(expectedId));
    }

    @Test
    public void givenAnInvalidId_whenCallsDeleteCategory_shouldOk() {
        final var expectedId = CategoryID.from("123");
        final var aCategory = Category.newCategory("Filmes", "Ação", true);

        Mockito.doThrow(new IllegalStateException("Gateway error"))
                .when(categoryGateway).deleteById(eq(expectedId));

        Assertions.assertThrows(IllegalStateException.class, () ->deleteCategoryUseCase.execute(expectedId.getValue()));
        Mockito.verify(categoryGateway, Mockito.times(1)).deleteById(eq(expectedId));
    }

}

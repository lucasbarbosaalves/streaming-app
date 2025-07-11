package com.github.lucasbarbosaalves.catalog.application.category.update;

import com.github.lucasbarbosaalves.catalog.application.UseCaseTest;
import com.github.lucasbarbosaalves.catalog.domain.category.Category;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway gateway;

    /* 1. Happy Path
       2. Invalid property
       3. Category inactive
       4. Error on gateway
     */

    @Override
    protected List<Object> getMocks() {
        return List.of(gateway);
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_thenReturnCategoryId() {
         final var expectedName = "Movies";
         final var expectedDescription = "Action movies";
         final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

         final var expectedId = aCategory.getId();

         final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        Mockito.when(gateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(Category.clone(aCategory)));

        Mockito.when(gateway.update(Mockito.any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(gateway, times(1)).findById(eq(expectedId));
        verify(gateway, times(1)).update(argThat(aCategoryUpdated ->
                Objects.equals(expectedName, aCategoryUpdated.getName())
                && Objects.equals(expectedDescription, aCategoryUpdated.getDescription())
                && Objects.equals(expectedIsActive, aCategoryUpdated.isActive())
                && Objects.equals(expectedId, aCategoryUpdated.getId())
                && Objects.equals(aCategoryUpdated.getCreatedAt(), aCategory.getCreatedAt()) // TODO: fix assertion
                && aCategory.getUpdatedAt().isBefore(aCategoryUpdated.getUpdatedAt())
                && Objects.isNull(aCategoryUpdated.getDeleteAt())
        ));

    }
    @Test
    public void givenAInvalidName_whenCallsUpdateCategory_thenReturnDomainException() {
        final var aCategory = Category.newCategory("Movies", "Action movies", true);
        final var expectedId = aCategory.getId();


        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), null, "Action movies", true);

        when(gateway.findById(eq(expectedId))).thenReturn(Optional.of(Category.clone(aCategory)));

        final var notification = useCase.execute(aCommand).getLeft();

        assertEquals(expectedErrorMessage, notification.firstError().message());
        assertEquals(expectedErrorCount, notification.getErrors().size());

        verify(gateway, times(0)).update(any());
    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsCreateCategory_thenReturnInactiveCategoryId() {
        final var aCategory = Category.newCategory("Movies", "Action movies", true);


        assertTrue(aCategory.isActive());
        assertNull(aCategory.getDeleteAt());
        
        final var expectedName = "Movies";
        final var expectedDescription = "Action movies";
        final var expectedIsActive = false;
        final var expectedId = aCategory.getId();


        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        when(gateway.findById(eq(expectedId))).thenReturn(Optional.of(Category.clone(aCategory)));

        when(this.gateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();


        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(gateway, times(1)).findById(eq(expectedId));

        verify(gateway, times(1)).update(argThat(updatedCategory ->
                Objects.equals(updatedCategory.getName(), expectedName) &&
                        Objects.equals(updatedCategory.getDescription(), expectedDescription) &&
                        Objects.equals(updatedCategory.isActive(), expectedIsActive) &&
                        Objects.nonNull(updatedCategory.getId()) &&
                        Objects.nonNull(updatedCategory.getCreatedAt()) &&
                        Objects.nonNull(updatedCategory.getUpdatedAt()) &&
                        Objects.nonNull(updatedCategory.getDeleteAt())
        ));
    }


}

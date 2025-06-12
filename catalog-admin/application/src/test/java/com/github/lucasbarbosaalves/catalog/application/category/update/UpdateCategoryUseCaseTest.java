package com.github.lucasbarbosaalves.catalog.application.category.update;

import com.github.lucasbarbosaalves.catalog.domain.category.Category;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway gateway;

    /* 1. Happy Path
       2. Invalid property
       3. Category inactive
       4. Error on gateway
     */

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_thenReturnCategoryId() {
         final var expectedName = "Movies";
         final var expectedDescription = "Action movies";
         final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

         final var expectedId = aCategory.getId();

         final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        Mockito.when(gateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(aCategory));

        Mockito.when(gateway.update(Mockito.any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(gateway, Mockito.times(1)).findById(Mockito.eq(expectedId));
        Mockito.verify(gateway, Mockito.times(1)).update(Mockito.argThat(aCategoryUpdated ->
                Objects.equals(expectedName, aCategoryUpdated.getName())
                && Objects.equals(expectedDescription, aCategoryUpdated.getDescription())
                && Objects.equals(expectedIsActive, aCategoryUpdated.isActive())
                && Objects.equals(expectedId, aCategoryUpdated.getId())
                && Objects.equals(aCategoryUpdated.getCreatedAt(), aCategory.getCreatedAt()) // TODO: fix assertion
                && aCategory.getUpdatedAt().isBefore(aCategoryUpdated.getUpdatedAt())
                && Objects.isNull(aCategoryUpdated.getDeleteAt())
        ));

    }

}

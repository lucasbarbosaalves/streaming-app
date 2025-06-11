package com.github.lucasbarbosaalves.catalog.application.category.create;

import com.github.lucasbarbosaalves.catalog.domain.category.CategoryGateway;
import com.github.lucasbarbosaalves.catalog.domain.exception.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway gateway;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_thenReturnCategoryId() {

        final var expectedName = "Movies";
        final var expectedDescription = "Action movies";
        final var expectedIsActive = true;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);


        when(this.gateway.create(any()))
                .thenAnswer(returnsFirstArg());


        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(this.gateway, times(1)).create(argThat(aCategory ->
                        Objects.equals(aCategory.getName(), expectedName) &&
                                Objects.equals(aCategory.getDescription(), expectedDescription) &&
                                Objects.equals(aCategory.isActive(), expectedIsActive) &&
                                Objects.nonNull(aCategory.getId()) &&
                                Objects.nonNull(aCategory.getCreatedAt()) &&
                                Objects.nonNull(aCategory.getUpdatedAt()) &&
                                Objects.isNull(aCategory.getDeleteAt())
                ));
    }

    @Test
     public void givenAInvalidName_whenCallsCreateCategory_thenReturnDomainException() {

        final String expectedDescription = "Action movies";
        final boolean expectedIsActive = true;

        final var aCommand = CreateCategoryCommand.with(null, expectedDescription, expectedIsActive);
        
        final var actualException = Assertions.assertThrows(DomainException.class, () ->  useCase.execute(aCommand));

        Assertions.assertEquals("'name' should not be null", actualException.getMessage());

        verify(gateway, never()).create(any());
        verify(gateway, times(0)).create(any());

    }


    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsCreateCategory_thenReturnInactiveCategoryId() {

        final var expectedName = "Movies";
        final var expectedDescription = "Action movies";
        final var expectedIsActive = false;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);


        when(this.gateway.create(any()))
                .thenAnswer(returnsFirstArg());


        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(this.gateway, times(1)).create(argThat(aCategory ->
                Objects.equals(aCategory.getName(), expectedName) &&
                        Objects.equals(aCategory.getDescription(), expectedDescription) &&
                        Objects.equals(aCategory.isActive(), expectedIsActive) &&
                        Objects.nonNull(aCategory.getId()) &&
                        Objects.nonNull(aCategory.getCreatedAt()) &&
                        Objects.nonNull(aCategory.getUpdatedAt()) &&
                        Objects.nonNull(aCategory.getDeleteAt())
        ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsGenericException_thenReturnAException() {

        final var expectedName = "Movies";
        final var expectedDescription = "Action movies";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Gateway error";

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);


        when(this.gateway.create(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(IllegalStateException.class, () ->  useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
        
        verify(this.gateway, times(1)).create(argThat(aCategory ->
                Objects.equals(aCategory.getName(), expectedName) &&
                        Objects.equals(aCategory.getDescription(), expectedDescription) &&
                        Objects.equals(aCategory.isActive(), expectedIsActive) &&
                        Objects.nonNull(aCategory.getId()) &&
                        Objects.nonNull(aCategory.getCreatedAt()) &&
                        Objects.nonNull(aCategory.getUpdatedAt()) &&
                        Objects.isNull(aCategory.getDeleteAt())
        ));
    }
}

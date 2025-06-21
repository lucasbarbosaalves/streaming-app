package com.github.lucasbarbosaalves.catalog.application.category.create;

import com.github.lucasbarbosaalves.catalog.IntegrationTest;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryGateway;
import com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@IntegrationTest
public class CreateCategoryUseCaseIT {

    @Autowired
    private CreateCategoryUseCase createCategoryUseCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @MockitoSpyBean
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_thenReturnCategoryId() {

        final var expectedName = "Movies";
        final var expectedDescription = "Action movies";
        final var expectedIsActive = true;

        assertEquals(0, categoryRepository.count());

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var actualOutput = createCategoryUseCase.execute(aCommand).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        final var actualCategory = categoryRepository.findById(actualOutput.id().getValue()).get();

        assertEquals(1, categoryRepository.count());

        assertNotNull(actualCategory);
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());


    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategory_thenReturnDomainException() {

        final String expectedDescription = "Action movies";
        final boolean expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        assertEquals(0, categoryRepository.count());

        final var aCommand = CreateCategoryCommand.with(null, expectedDescription, expectedIsActive);

        final var notification = createCategoryUseCase.execute(aCommand).getLeft();

        assertEquals(0, categoryRepository.count());

        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());

        verify(categoryGateway, times(0)).create(any());
    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsCreateCategory_thenReturnInactiveCategoryId() {

        final var expectedName = "Movies";
        final var expectedDescription = "Action movies";
        final var expectedIsActive = false;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var actualOutput = createCategoryUseCase.execute(aCommand).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        final var actualCategory = categoryRepository.findById(actualOutput.id().getValue()).get();

        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNotNull(actualCategory.getDeletedAt());

    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsGenericException_thenReturnAException() {

        final var expectedName = "Movies";
        final var expectedDescription = "Action movies";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Gateway error";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        doThrow(new IllegalStateException(expectedErrorMessage))
                .when(categoryGateway).create(any());
        
        final var notification = createCategoryUseCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());

    }

}

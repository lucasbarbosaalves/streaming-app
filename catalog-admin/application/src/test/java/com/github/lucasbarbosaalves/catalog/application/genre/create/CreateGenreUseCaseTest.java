package com.github.lucasbarbosaalves.catalog.application.genre.create;

import com.github.lucasbarbosaalves.catalog.application.UseCaseTest;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryGateway;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import com.github.lucasbarbosaalves.catalog.domain.exception.NotificationException;
import com.github.lucasbarbosaalves.catalog.domain.genre.GenreGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateGenreUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateGenreUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Mock
    private GenreGateway genreGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway, genreGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsCreateGenre_thenShouldReturnGenreId() {
        //given
        final var expectedName = "Action";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand =
                CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));

        when(genreGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(genreGateway, times(1)).create(argThat(aGenre ->
                Objects.equals(aGenre.getName(), expectedName) &&
                        Objects.equals(aGenre.isActive(), expectedIsActive) &&
                        Objects.equals(aGenre.getCategories(), expectedCategories) &&
                        Objects.nonNull(aGenre.getId()) &&
                        Objects.nonNull(aGenre.getCreatedAt()) &&
                        Objects.nonNull(aGenre.getUpdatedAt()) &&
                        Objects.isNull(aGenre.getDeletedAt())
        ));

    }

    @Test
    public void givenAValidCommandWithInactiveGenre_whenCallsCreateGenre_thenShouldReturnGenreId() {
        //given
        final var expectedName = "Action";
        final var expectedIsActive = false;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand =
                CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));

        when(genreGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        // when

        final var actualOutput = useCase.execute(aCommand);

        // then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(genreGateway, times(1)).create(argThat(aGenre ->
                Objects.equals(aGenre.getName(), expectedName) &&
                        Objects.equals(aGenre.isActive(), expectedIsActive) &&
                        Objects.equals(aGenre.getCategories(), expectedCategories) &&
                        Objects.nonNull(aGenre.getId()) &&
                        Objects.nonNull(aGenre.getCreatedAt()) &&
                        Objects.nonNull(aGenre.getUpdatedAt()) &&
                        Objects.nonNull(aGenre.getDeletedAt())
        ));

    }

    @Test
    public void givenAValidCommandWithCategories_whenCallsCreateGenre_thenShouldReturnGenreId() {
        //given
        final var expectedName = "Action";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(
                CategoryID.from("123"),
                CategoryID.from("456")
        );

        final var aCommand =
                CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));

        when(categoryGateway.existsByIds(any()))
                .thenReturn(expectedCategories);

        when(genreGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1)).existsByIds(expectedCategories);

        verify(genreGateway, times(1)).create(argThat(aGenre ->
                Objects.equals(aGenre.getName(), expectedName) &&
                        Objects.equals(aGenre.isActive(), expectedIsActive) &&
                        Objects.equals(aGenre.getCategories(), expectedCategories) &&
                        Objects.nonNull(aGenre.getId()) &&
                        Objects.nonNull(aGenre.getCreatedAt()) &&
                        Objects.nonNull(aGenre.getUpdatedAt()) &&
                        Objects.isNull(aGenre.getDeletedAt())
        ));
    }

    @Test
    public void givenAInvalidEmptyName_whenCallsCreateGenre_thenShouldReturnDomainException() {
        //given
        final String expectedName = " ";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        final var aCommand =
                CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));

        //when
        final var actualException = assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        //then
        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

        // Verificar que os métodos não foram chamados
        verify(categoryGateway, times(0)).existsByIds(any());
        verify(genreGateway, times(0)).create(any());
    }


    @Test
    public void givenValidCommand_whenCallsCreateGenreAndSomeCategoriesDoesNotExists_thenShouldReturnDomainException() {
        //given
        final var movies = CategoryID.from("123");
        final var action = CategoryID.from("456");
        final var documentaries = CategoryID.from("789");

        final String expectedName = "Action";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(movies, action, documentaries);

        final var expectedErrorMessage = "Some categories could not be found: 456, 789";
        final var expectedErrorCount = 1;

        when(categoryGateway.existsByIds(any()))
                .thenReturn(List.of(movies));

        final var aCommand =
                CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));

        //when
        final var actualException = assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        //then
        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

        verify(categoryGateway, times(1)).existsByIds(any());
        verify(genreGateway, times(0)).create(any());
    }

    @Test
    public void givenInvalidName_whenCallsCreateGenreAndSomeCategoriesDoesNotExists_thenShouldReturnDomainException() {
        //given
        final var movies = CategoryID.from("123");
        final var action = CategoryID.from("456");
        final var documentaries = CategoryID.from("789");

        final String expectedName = " ";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(movies, action, documentaries);

        final var expectedErrorMessageOne = "Some categories could not be found: 456, 789";
        final var expectedErrorMessageTwo = "'name' should not be empty";
        final var expectedErrorCount = 2;

        when(categoryGateway.existsByIds(any()))
                .thenReturn(List.of(movies));

        final var aCommand =
                CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));

        //when
        final var actualException = assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        //then
        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessageOne, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorMessageTwo, actualException.getErrors().get(1).message());

        verify(categoryGateway, times(1)).existsByIds(any());
        verify(genreGateway, times(0)).create(any());
    }

}

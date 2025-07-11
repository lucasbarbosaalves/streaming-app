package com.github.lucasbarbosaalves.catalog.application.genre.update;

import com.github.lucasbarbosaalves.catalog.application.UseCaseTest;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryGateway;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import com.github.lucasbarbosaalves.catalog.domain.exception.NotificationException;
import com.github.lucasbarbosaalves.catalog.domain.genre.Genre;
import com.github.lucasbarbosaalves.catalog.domain.genre.GenreGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdateGenreUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateGenreUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Mock
    private GenreGateway genreGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway, genreGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateGenre_thenShouldReturnGenreID() {
        // given
        final var genre = Genre.newGenre("ação", true);

        final var expectedId = genre.getId();
        final var expectedName = "Action";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var command = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName, expectedIsActive,
                asString(expectedCategories));

        when(genreGateway.findById(any()))
                .thenReturn(Optional.of(Genre.with(genre)));

        when(genreGateway.update(any()))
                .thenAnswer(returnsFirstArg());
        // when
        final var actualOutput = useCase.execute(command);

        // then
        assertNotNull(actualOutput);
        assertEquals(expectedId.getValue(), actualOutput.id());

        verify(genreGateway, times(1)).findById(eq(expectedId));
        verify(genreGateway, times(1)).update(argThat(aUpdateGenre ->
                Objects.equals(expectedId, aUpdateGenre.getId()) &&
                        Objects.equals(expectedName, aUpdateGenre.getName()) &&
                        Objects.equals(expectedIsActive, aUpdateGenre.isActive()) &&
                        Objects.equals(expectedCategories, aUpdateGenre.getCategories()) &&
                        Objects.equals(genre.getCreatedAt(), aUpdateGenre.getCreatedAt()) &&
                        genre.getUpdatedAt().isBefore(aUpdateGenre.getUpdatedAt()) &&
                        Objects.isNull(aUpdateGenre.getDeletedAt())));
    }

    @Test
    public void givenAValidCommandWithCategories_whenCallsUpdateGenre_thenShouldReturnGenreID() {
        // given
        final var genre = Genre.newGenre("ação", true);

        final var expectedId = genre.getId();
        final var expectedName = "Action";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of(
                CategoryID.from("123"),
                CategoryID.from("456")
        );

        final var command = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName, expectedIsActive,
                asString(expectedCategories));

        when(genreGateway.findById(any()))
                .thenReturn(Optional.of(Genre.with(genre)));

        when(categoryGateway.existsByIds(any()))
                .thenReturn(expectedCategories);

        when(genreGateway.update(any()))
                .thenAnswer(returnsFirstArg());
        // when
        final var actualOutput = useCase.execute(command);

        // then
        assertNotNull(actualOutput);
        assertEquals(expectedId.getValue(), actualOutput.id());

        verify(genreGateway, times(1)).findById(eq(expectedId));
        verify(categoryGateway, times(1)).existsByIds(expectedCategories);
        verify(genreGateway, times(1)).update(argThat(aUpdateGenre ->
                Objects.equals(expectedId, aUpdateGenre.getId()) &&
                        Objects.equals(expectedName, aUpdateGenre.getName()) &&
                        Objects.equals(expectedIsActive, aUpdateGenre.isActive()) &&
                        Objects.equals(expectedCategories, aUpdateGenre.getCategories()) &&
                        Objects.equals(genre.getCreatedAt(), aUpdateGenre.getCreatedAt()) &&
                        genre.getUpdatedAt().isBefore(aUpdateGenre.getUpdatedAt()) &&
                        Objects.isNull(aUpdateGenre.getDeletedAt())));
    }

    @Test
    public void givenAnInvalidName_whenCallsUpdateGenre_thenShouldReturnNotificationException() {
        // given
        final var genre = Genre.newGenre("ação", true);

        final var expectedId = genre.getId();
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var command = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName, expectedIsActive,
                asString(expectedCategories));

        when(genreGateway.findById(any()))
                .thenReturn(Optional.of(Genre.with(genre)));

        // when
        final var actualOutput = assertThrows(NotificationException.class,() -> useCase.execute(command));

        // then
        assertEquals(expectedErrorCount, actualOutput.getErrors().size());
        assertEquals(expectedErrorMessage, actualOutput.getErrors().getFirst().message());

        verify(genreGateway, times(1)).findById(eq(expectedId));
        verify(categoryGateway, times(0)).existsByIds(any());
        verify(genreGateway, times(0)).update(any());
    }

    @Test
    public void givenAnInvalidName_whenCallsUpdateGenreWithSomeCategoriesDoesNotExists_thenShouldReturnNotificationException() {
        // given
        final var movies = CategoryID.from("123");
        final var series = CategoryID.from("456");
        final var documentaries = CategoryID.from("789");


        final var genre = Genre.newGenre("ação", true);

        final var expectedId = genre.getId();
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedCategories = List.of(
                movies, series, documentaries
        );

        final var expectedErrorMessageOne = "Some categories could not be found: 456, 789";
        final var expectedErrorMessageTwo = "'name' should not be null";
        final var expectedErrorCount = 2;

        final var command = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName, expectedIsActive,
                asString(expectedCategories));

        when(genreGateway.findById(any()))
                .thenReturn(Optional.of(Genre.with(genre)));

        when(categoryGateway.existsByIds(any()))
                .thenReturn(List.of(movies));
        // when
        final var actualOutput = assertThrows(NotificationException.class,() -> useCase.execute(command));

        // then
        assertEquals(expectedErrorCount, actualOutput.getErrors().size());
        assertEquals(expectedErrorMessageOne, actualOutput.getErrors().getFirst().message());
        assertEquals(expectedErrorMessageTwo, actualOutput.getErrors().get(1).message());

        verify(genreGateway, times(1)).findById(eq(expectedId));
        verify(categoryGateway, times(1)).existsByIds(eq(expectedCategories));
        verify(genreGateway, times(0)).update(any());
    }

    @Test
    public void givenAValidCommandWithInactiveGenre_whenCallsUpdateGenre_thenShouldReturnGenreId() {
        // given
        final var aGenre = Genre.newGenre("acao", true);

        final var expectedId = aGenre.getId();
        final var expectedName = "Ação";
        final var expectedIsActive = false;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );

        when(genreGateway.findById(any()))
                .thenReturn(Optional.of(Genre.with(aGenre)));

        when(genreGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        assertTrue(aGenre.isActive());
        assertNull(aGenre.getDeletedAt());

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        assertNotNull(actualOutput);
        assertEquals(expectedId.getValue(), actualOutput.id());

        verify(genreGateway, times(1)).findById(eq(expectedId));

        verify(genreGateway, times(1)).update(argThat(aUpdatedGenre ->
                Objects.equals(expectedId, aUpdatedGenre.getId())
                        && Objects.equals(expectedName, aUpdatedGenre.getName())
                        && Objects.equals(expectedIsActive, aUpdatedGenre.isActive())
                        && Objects.equals(expectedCategories, aUpdatedGenre.getCategories())
                        && Objects.equals(aGenre.getCreatedAt(), aUpdatedGenre.getCreatedAt())
                        && aGenre.getUpdatedAt().isBefore(aUpdatedGenre.getUpdatedAt())
                        && Objects.nonNull(aUpdatedGenre.getDeletedAt())
        ));
    }


    private List<String> asString(final List<CategoryID> categories) {
        return categories.stream()
                .map(CategoryID::getValue)
                .toList();
    }
}

package com.github.lucasbarbosaalves.catalog.application.genre.delete;

import com.github.lucasbarbosaalves.catalog.application.UseCaseTest;
import com.github.lucasbarbosaalves.catalog.domain.genre.Genre;
import com.github.lucasbarbosaalves.catalog.domain.genre.GenreGateway;
import com.github.lucasbarbosaalves.catalog.domain.genre.GenreID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class DeleteGenreUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteGenreUseCase useCase;

    @Mock
    private GenreGateway genreGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(genreGateway);
    }

    @Test
    public void givenAValidGenreId_whenCallsDeleteGenre_shouldDeleteGenre() {
        // given
        final var aGenre = Genre.newGenre("Ação", true);

        final var expectedId = aGenre.getId();

        doNothing()
                .when(genreGateway).deleteById(any());

        // when
        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // when
        Mockito.verify(genreGateway, times(1)).deleteById(expectedId);
    }

    @Test
    public void givenAnInvalidGenreId_whenCallsDeleteGenre_shouldBeOk() {
        // given
        final var expectedId = GenreID.from("123");

        doNothing()
                .when(genreGateway).deleteById(any());

        // when
        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // when
        Mockito.verify(genreGateway, times(1)).deleteById(expectedId);
    }

    @Test
    public void givenAValidGenreId_whenCallsDeleteGenreAndGatewayThrowsUnexpectedError_shouldReceiveException() {
        // given
        final var aGenre = Genre.newGenre("Ação", true);
        final var expectedId = aGenre.getId();

        doThrow(new IllegalStateException("Gateway error"))
                .when(genreGateway).deleteById(any());

        // when
        assertThrows(IllegalStateException.class, () -> {
            useCase.execute(expectedId.getValue());
        });

        // when
        Mockito.verify(genreGateway, times(1)).deleteById(expectedId);
    }
}
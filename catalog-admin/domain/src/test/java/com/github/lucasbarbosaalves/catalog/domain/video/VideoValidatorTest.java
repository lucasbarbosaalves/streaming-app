package com.github.lucasbarbosaalves.catalog.domain.video;

import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberID;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import com.github.lucasbarbosaalves.catalog.domain.exception.DomainException;
import com.github.lucasbarbosaalves.catalog.domain.genre.GenreID;
import com.github.lucasbarbosaalves.catalog.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VideoValidatorTest {

    @Test
    public void givenNullTitle_whenCallsValidate_shouldReceiveError() {
        // Given
        final String expectedTitle = null;
        final var expectedDescription = "A description";
        final var expectedLaunchedAt = Year.of(2021);
        final var expectedDuration = 120.0;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedCastMembers = Set.of(CastMemberID.unique());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'title' should not be null";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedCastMembers
        );

        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // When
        final var actualError = assertThrows(DomainException.class, () -> validator.validate());

        // Then
        assertEquals(expectedErrorCount, actualError.getErrors().size());
        assertEquals(expectedErrorMessage, actualError.getErrors().getFirst().message());
    }

    @Test
    public void givenTitleWithLongLength_whenCallsValidate_shouldReceiveError() {
        // Given
        final String expectedTitle = "A".repeat(256);
        final var expectedDescription = "A description";
        final var expectedLaunchedAt = Year.of(2021);
        final var expectedDuration = 120.0;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedCastMembers = Set.of(CastMemberID.unique());

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'title' must be between 1 and 255 characters";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedCastMembers
        );

        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // When
        final var actualError = assertThrows(DomainException.class, () -> validator.validate());

        // Then
        assertEquals(expectedErrorCount, actualError.getErrors().size());
        assertEquals(expectedErrorMessage, actualError.getErrors().getFirst().message());

    }

    @Test
    public void givenEmptyTitle_whenCallsValidate_shouldReceiveError() {
        // Given
        final String expectedTitle = "";
        final var expectedDescription = "A description";
        final var expectedLaunchedAt = Year.of(2021);
        final var expectedDuration = 120.0;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedCastMembers = Set.of(CastMemberID.unique());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'title' should not be empty";

        // When
        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedCastMembers
        );

        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // When
        final var actualError = assertThrows(DomainException.class, () -> validator.validate());

        // Then
        assertEquals(expectedErrorCount, actualError.getErrors().size());
        assertEquals(expectedErrorMessage, actualError.getErrors().getFirst().message());
    }

    @Test
    public void givenEmptyDescription_whenCallsValidate_shouldReceiveError() {
        // Given
        final String expectedTitle = "Matrix Resurrections";
        final String expectedDescription = " ";
        final var expectedLaunchedAt = Year.of(2021);
        final var expectedDuration = 120.0;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedCastMembers = Set.of(CastMemberID.unique());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'description' should not be empty";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedCastMembers
        );

        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // When
        final var actualError = assertThrows(DomainException.class, () -> validator.validate());

        // Then
        assertEquals(expectedErrorCount, actualError.getErrors().size());
        assertEquals(expectedErrorMessage, actualError.getErrors().getFirst().message());
    }

    @Test
    public void givenDescriptionWithLengthGreaterThan4000_whenCallsValidate_shouldReceiveError() {
        // Given
        final String expectedTitle = "Matrix Resurrections";
        final String expectedDescription = "A".repeat(4001);
        final var expectedLaunchedAt = Year.of(2021);
        final var expectedDuration = 120.0;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedCastMembers = Set.of(CastMemberID.unique());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'description' must be between 1 and 4000 characters";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedCastMembers
        );

        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // When
        final var actualError = assertThrows(DomainException.class, () -> validator.validate());

        // Then
        assertEquals(expectedErrorCount, actualError.getErrors().size());
        assertEquals(expectedErrorMessage, actualError.getErrors().getFirst().message());
    }

    @Test
    public void givenNullLaunchedAt_whenCallsValidate_shouldReceiveError() {
        // Given
        final String expectedTitle = "Matrix Resurrections";
        final String expectedDescription = "Maximum action and adventure";
        final Year expectedLaunchedAt = null;
        final var expectedDuration = 120.0;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedCastMembers = Set.of(CastMemberID.unique());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'launchedAt' should not be null";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedCastMembers
        );

        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // When
        final var actualError = assertThrows(DomainException.class, () -> validator.validate());

        // Then
        assertEquals(expectedErrorCount, actualError.getErrors().size());
        assertEquals(expectedErrorMessage, actualError.getErrors().getFirst().message());
    }


    @Test
    public void givenNullRating_whenCallsValidate_shouldReceiveError() {
        // Given
        final String expectedTitle = "Matrix Resurrections";
        final String expectedDescription = "Maximum action and adventure";
        final var expectedLaunchedAt = Year.of(2021);
        final var expectedDuration = 120.0;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final Rating expectedRating = null;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedCastMembers = Set.of(CastMemberID.unique());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'rating' should not be null";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedCastMembers
        );

        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // When
        final var actualError = assertThrows(DomainException.class, () -> validator.validate());

        // Then
        assertEquals(expectedErrorCount, actualError.getErrors().size());
        assertEquals(expectedErrorMessage, actualError.getErrors().getFirst().message());
    }


}


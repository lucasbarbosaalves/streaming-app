package com.github.lucasbarbosaalves.catalog.domain.video;

import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberID;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import com.github.lucasbarbosaalves.catalog.domain.genre.GenreID;
import com.github.lucasbarbosaalves.catalog.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class VideoTest {

    @Test
    public void givenValidParams_whenCallsNewVideo_shouldInstantiate() {
        // Given
        final var expectedTitle = "The Matrix";
        final var expectedDescription = """
                 A computer hacker learns from mysterious rebels about the true nature of his reality and his role in\s
                 the war against its controllers.
                \s""";
        final var expectedYear = Year.of(2018);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedCastMembers = Set.of(CastMemberID.unique());

        // When
        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedYear,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedCastMembers
        );

        // Then
        assertNotNull(actualVideo);
        assertNotNull(actualVideo.getId());
        assertEquals(expectedTitle, actualVideo.getTitle());
        assertEquals(expectedDescription, actualVideo.getDescription());
        assertEquals(expectedYear, actualVideo.getLaunchedAt());
        assertEquals(expectedDuration, actualVideo.getDuration());
        assertEquals(expectedOpened, actualVideo.getOpened());
        assertEquals(expectedPublished, actualVideo.getPublished());
        assertEquals(expectedRating, actualVideo.getRating());
        assertEquals(expectedCategories, actualVideo.getCategories());
        assertEquals(expectedGenres, actualVideo.getGenres());
        assertEquals(expectedCastMembers, actualVideo.getCastMembers());
        assertTrue(actualVideo.getVideo().isEmpty());
        assertTrue(actualVideo.getTrailer().isEmpty());
        assertTrue(actualVideo.getBanner().isEmpty());
        assertTrue(actualVideo.getThumbnail().isEmpty());
        assertTrue(actualVideo.getThumbnailHalf().isEmpty());

        assertDoesNotThrow(() -> actualVideo.validate(new ThrowsValidationHandler()));
    }


    @Test
    public void givenValidVideo_whenCallsUpdate_shouldReturnUpdated() {
        // given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = """
                Disclaimer: o estudo de caso apresentado tem fins educacionais e representa nossas opiniões pessoais.
                Esse vídeo faz parte da Imersão Full Stack && Full Cycle.
                Para acessar todas as aulas, lives e desafios, acesse:
                https://imersao.fullcycle.com.br/
                """;
        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var aVideo = Video.newVideo(
                "Test title",
                "Lalala description",
                Year.of(1888),
                0.0,
                true,
                true,
                Rating.AGE_10,
                Set.of(),
                Set.of(),
                Set.of()
        );

        // when
        final var actualVideo = Video.with(aVideo).update(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );

        // then
       assertNotNull(actualVideo);
       assertNotNull(actualVideo.getId());
       assertEquals(aVideo.getCreatedAt(), actualVideo.getCreatedAt());
       assertEquals(expectedTitle, actualVideo.getTitle());
       assertEquals(expectedDescription, actualVideo.getDescription());
       assertEquals(expectedLaunchedAt, actualVideo.getLaunchedAt());
       assertEquals(expectedDuration, actualVideo.getDuration());
       assertEquals(expectedOpened, actualVideo.getOpened());
       assertEquals(expectedPublished, actualVideo.getPublished());
       assertEquals(expectedRating, actualVideo.getRating());
       assertEquals(expectedCategories, actualVideo.getCategories());
       assertEquals(expectedGenres, actualVideo.getGenres());
       assertEquals(expectedMembers, actualVideo.getCastMembers());
       assertTrue(actualVideo.getVideo().isEmpty());
       assertTrue(actualVideo.getTrailer().isEmpty());
       assertTrue(actualVideo.getBanner().isEmpty());
       assertTrue(actualVideo.getThumbnail().isEmpty());
       assertTrue(actualVideo.getThumbnailHalf().isEmpty());

       assertDoesNotThrow(() -> actualVideo.validate(new ThrowsValidationHandler()));
    }

    @Test
    public void givenValidVideo_whenCallsSetVideo_shouldReturnUpdated() {
        // given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = """
                Disclaimer: o estudo de caso apresentado tem fins educacionais e representa nossas opiniões pessoais.
                Esse vídeo faz parte da Imersão Full Stack && Full Cycle.
                Para acessar todas as aulas, lives e desafios, acesse:
                https://imersao.fullcycle.com.br/
                """;
        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var aVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );

        final var aVideoMedia =
                AudioVideoMedia.with("abc", "Video.mp4", "/123/videos", "", MediaStatus.PENDING);

        // when
        final var actualVideo = Video.with(aVideo).setVideo(aVideoMedia);

        // then
       assertNotNull(actualVideo);
       assertNotNull(actualVideo.getId());
       assertEquals(aVideo.getCreatedAt(), actualVideo.getCreatedAt());
       assertEquals(expectedTitle, actualVideo.getTitle());
       assertEquals(expectedDescription, actualVideo.getDescription());
       assertEquals(expectedLaunchedAt, actualVideo.getLaunchedAt());
       assertEquals(expectedDuration, actualVideo.getDuration());
       assertEquals(expectedOpened, actualVideo.getOpened());
       assertEquals(expectedPublished, actualVideo.getPublished());
       assertEquals(expectedRating, actualVideo.getRating());
       assertEquals(expectedCategories, actualVideo.getCategories());
       assertEquals(expectedGenres, actualVideo.getGenres());
       assertEquals(expectedMembers, actualVideo.getCastMembers());
       assertEquals(aVideoMedia, actualVideo.getVideo().get());
       assertTrue(actualVideo.getTrailer().isEmpty());
       assertTrue(actualVideo.getBanner().isEmpty());
       assertTrue(actualVideo.getThumbnail().isEmpty());
       assertTrue(actualVideo.getThumbnailHalf().isEmpty());

       assertDoesNotThrow(() -> actualVideo.validate(new ThrowsValidationHandler()));
    }

    @Test
    public void givenValidVideo_whenCallsSetTrailer_shouldReturnUpdated() {
        // given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = """
                Disclaimer: o estudo de caso apresentado tem fins educacionais e representa nossas opiniões pessoais.
                Esse vídeo faz parte da Imersão Full Stack && Full Cycle.
                Para acessar todas as aulas, lives e desafios, acesse:
                https://imersao.fullcycle.com.br/
                """;
        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var aVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );

        final var aTrailerMedia =
                AudioVideoMedia.with("abc", "Trailer.mp4", "/123/videos", "", MediaStatus.PENDING);

        // when
        final var actualVideo = Video.with(aVideo).setTrailer(aTrailerMedia);

        // then
       assertNotNull(actualVideo);
       assertNotNull(actualVideo.getId());
       assertEquals(aVideo.getCreatedAt(), actualVideo.getCreatedAt());
       assertEquals(expectedTitle, actualVideo.getTitle());
       assertEquals(expectedDescription, actualVideo.getDescription());
       assertEquals(expectedLaunchedAt, actualVideo.getLaunchedAt());
       assertEquals(expectedDuration, actualVideo.getDuration());
       assertEquals(expectedOpened, actualVideo.getOpened());
       assertEquals(expectedPublished, actualVideo.getPublished());
       assertEquals(expectedRating, actualVideo.getRating());
       assertEquals(expectedCategories, actualVideo.getCategories());
       assertEquals(expectedGenres, actualVideo.getGenres());
       assertEquals(expectedMembers, actualVideo.getCastMembers());
       assertTrue(actualVideo.getVideo().isEmpty());
       assertEquals(aTrailerMedia, actualVideo.getTrailer().get());
       assertTrue(actualVideo.getBanner().isEmpty());
       assertTrue(actualVideo.getThumbnail().isEmpty());
       assertTrue(actualVideo.getThumbnailHalf().isEmpty());

       assertDoesNotThrow(() -> actualVideo.validate(new ThrowsValidationHandler()));
    }

    @Test
    public void givenValidVideo_whenCallsSetBanner_shouldReturnUpdated() {
        // given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = """
                Disclaimer: o estudo de caso apresentado tem fins educacionais e representa nossas opiniões pessoais.
                Esse vídeo faz parte da Imersão Full Stack && Full Cycle.
                Para acessar todas as aulas, lives e desafios, acesse:
                https://imersao.fullcycle.com.br/
                """;
        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var aVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );

        final var aBannerMedia =
                ImageMedia.with("abc", "Trailer.mp4", "/123/videos");

        // when
        final var actualVideo = Video.with(aVideo).setBanner(aBannerMedia);

        // then
       assertNotNull(actualVideo);
       assertNotNull(actualVideo.getId());
       assertEquals(aVideo.getCreatedAt(), actualVideo.getCreatedAt());
       assertEquals(expectedTitle, actualVideo.getTitle());
       assertEquals(expectedDescription, actualVideo.getDescription());
       assertEquals(expectedLaunchedAt, actualVideo.getLaunchedAt());
       assertEquals(expectedDuration, actualVideo.getDuration());
       assertEquals(expectedOpened, actualVideo.getOpened());
       assertEquals(expectedPublished, actualVideo.getPublished());
       assertEquals(expectedRating, actualVideo.getRating());
       assertEquals(expectedCategories, actualVideo.getCategories());
       assertEquals(expectedGenres, actualVideo.getGenres());
       assertEquals(expectedMembers, actualVideo.getCastMembers());
       assertTrue(actualVideo.getVideo().isEmpty());
       assertTrue(actualVideo.getTrailer().isEmpty());
       assertEquals(aBannerMedia, actualVideo.getBanner().get());
       assertTrue(actualVideo.getThumbnail().isEmpty());
       assertTrue(actualVideo.getThumbnailHalf().isEmpty());

       assertDoesNotThrow(() -> actualVideo.validate(new ThrowsValidationHandler()));
    }

    @Test
    public void givenValidVideo_whenCallsSetThumbnail_shouldReturnUpdated() {
        // given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = """
                Disclaimer: o estudo de caso apresentado tem fins educacionais e representa nossas opiniões pessoais.
                Esse vídeo faz parte da Imersão Full Stack && Full Cycle.
                Para acessar todas as aulas, lives e desafios, acesse:
                https://imersao.fullcycle.com.br/
                """;
        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var aVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );

        final var aThumbMedia =
                ImageMedia.with("abc", "Trailer.mp4", "/123/videos");

        // when
        final var actualVideo = Video.with(aVideo).setThumbnail(aThumbMedia);

        // then
       assertNotNull(actualVideo);
       assertNotNull(actualVideo.getId());
       assertEquals(aVideo.getCreatedAt(), actualVideo.getCreatedAt());
       assertEquals(expectedTitle, actualVideo.getTitle());
       assertEquals(expectedDescription, actualVideo.getDescription());
       assertEquals(expectedLaunchedAt, actualVideo.getLaunchedAt());
       assertEquals(expectedDuration, actualVideo.getDuration());
       assertEquals(expectedOpened, actualVideo.getOpened());
       assertEquals(expectedPublished, actualVideo.getPublished());
       assertEquals(expectedRating, actualVideo.getRating());
       assertEquals(expectedCategories, actualVideo.getCategories());
       assertEquals(expectedGenres, actualVideo.getGenres());
       assertEquals(expectedMembers, actualVideo.getCastMembers());
       assertTrue(actualVideo.getVideo().isEmpty());
       assertTrue(actualVideo.getTrailer().isEmpty());
       assertTrue(actualVideo.getBanner().isEmpty());
       assertEquals(aThumbMedia, actualVideo.getThumbnail().get());
       assertTrue(actualVideo.getThumbnailHalf().isEmpty());

       assertDoesNotThrow(() -> actualVideo.validate(new ThrowsValidationHandler()));
    }

    @Test
    public void givenValidVideo_whenCallsSetThumbnailHalf_shouldReturnUpdated() {
        // given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = """
                Disclaimer: o estudo de caso apresentado tem fins educacionais e representa nossas opiniões pessoais.
                Esse vídeo faz parte da Imersão Full Stack && Full Cycle.
                Para acessar todas as aulas, lives e desafios, acesse:
                https://imersao.fullcycle.com.br/
                """;
        final var expectedLaunchedAt = Year.of(2022);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var aVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );

        final var aThumbMedia =
                ImageMedia.with("abc", "Trailer.mp4", "/123/videos");

        // when
        final var actualVideo = Video.with(aVideo).setThumbnailHalf(aThumbMedia);

        // then
       assertNotNull(actualVideo);
       assertNotNull(actualVideo.getId());
       assertEquals(aVideo.getCreatedAt(), actualVideo.getCreatedAt());
       assertEquals(expectedTitle, actualVideo.getTitle());
       assertEquals(expectedDescription, actualVideo.getDescription());
       assertEquals(expectedLaunchedAt, actualVideo.getLaunchedAt());
       assertEquals(expectedDuration, actualVideo.getDuration());
       assertEquals(expectedOpened, actualVideo.getOpened());
       assertEquals(expectedPublished, actualVideo.getPublished());
       assertEquals(expectedRating, actualVideo.getRating());
       assertEquals(expectedCategories, actualVideo.getCategories());
       assertEquals(expectedGenres, actualVideo.getGenres());
       assertEquals(expectedMembers, actualVideo.getCastMembers());
       assertTrue(actualVideo.getVideo().isEmpty());
       assertTrue(actualVideo.getTrailer().isEmpty());
       assertTrue(actualVideo.getBanner().isEmpty());
       assertTrue(actualVideo.getThumbnail().isEmpty());
       assertEquals(aThumbMedia, actualVideo.getThumbnailHalf().get());

       assertDoesNotThrow(() -> actualVideo.validate(new ThrowsValidationHandler()));
    }
}

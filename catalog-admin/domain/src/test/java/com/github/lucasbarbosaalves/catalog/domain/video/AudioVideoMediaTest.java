package com.github.lucasbarbosaalves.catalog.domain.video;

import com.github.lucasbarbosaalves.catalog.domain.utils.IdUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudioVideoMediaTest {

    @Test
    public void givenValidParams_whenCallsNewAudioVideo_ShouldReturnInstance() {
        // given
        final var expectedId = IdUtils.uuid();
        final var expectedChecksum = "abc";
        final var expectedName = "Banner.png";
        final var expectedRawLocation = "/images/ac";
        final var expectedEncodedLocation = "/images/ac-encoded";
        final var expectedStatus = MediaStatus.PENDING;

        // when
        final var actualVideo =
                AudioVideoMedia.with(expectedId, expectedChecksum, expectedName, expectedRawLocation, expectedEncodedLocation, expectedStatus);

        // then
        assertNotNull(actualVideo);
        assertEquals(expectedId, actualVideo.id());
        assertEquals(expectedChecksum, actualVideo.checksum());
        assertEquals(expectedName, actualVideo.name());
        assertEquals(expectedRawLocation, actualVideo.rawLocation());
        assertEquals(expectedEncodedLocation, actualVideo.encodedLocation());
        assertEquals(expectedStatus, actualVideo.status());
    }

    @Test
    public void givenTwoVideosWithSameChecksumAndLocation_whenCallsEquals_ShouldReturnTrue() {
        // given
        final var expectedChecksum = "abc";
        final var expectedRawLocation = "/images/ac";

        final var img1 =
                AudioVideoMedia.with(expectedChecksum, "Random", expectedRawLocation);

        final var img2 =
                AudioVideoMedia.with(expectedChecksum, "Simple", expectedRawLocation);

        // then
        assertEquals(img1, img2);
        assertNotSame(img1, img2);
    }

    @Test
    public void givenInvalidParams_whenCallsWith_ShouldReturnError() {
        assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with(null, "131", "Random", "/videos", "/videos", MediaStatus.PENDING)
        );

        assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with("id", "abc", null, "/videos", "/videos", MediaStatus.PENDING)
        );

        assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with("id", "abc", "Random", null, "/videos", MediaStatus.PENDING)
        );

        assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with("id", "abc", "Random", "/videos", null, MediaStatus.PENDING)
        );

        assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with("id", "abc", "Random", "/videos", "/videos", null)
        );
    }
}
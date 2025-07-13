package com.github.lucasbarbosaalves.catalog.domain.video;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudioVideoMediaTest {

    @Test
    public void givenAValidParams_whenCallsNewAudioVideo_shouldReturnInstance() {
        // given
        final var expectedChecksum = "checksum";
        final var expectedName = "banner.png";
        final var expectedRawLocation = "/images/banner.png";
        final var expectedEncodedLocation = "/images/banner.png";
        final var expectedStatus = MediaStatus.PENDING;

        // when
        final var actual = AudioVideoMedia.with(expectedChecksum, expectedName, expectedRawLocation, expectedEncodedLocation, expectedStatus);

        assertNotNull(actual);
        assertEquals(expectedChecksum, actual.checksum());
        assertEquals(expectedName, actual.name());
        assertEquals(expectedRawLocation, actual.rawLocation());
        assertEquals(expectedEncodedLocation, actual.encodedLocation());
        assertEquals(expectedStatus, actual.status());

    }

    @Test
    public void givenSamesValues_whenCallsEquals_shouldReturnTrue() {
        // given
        final var expectedChecksum = "checksum";
        final var expectedRawLocation = "/images/banner.png";
        final var expectedEncodedLocation = "/images/banner.png";
        final var expectedStatus = MediaStatus.PENDING;

        final var audioVideo1 = AudioVideoMedia.with(expectedChecksum, "AudioVideo1", expectedRawLocation, expectedEncodedLocation, expectedStatus);
        final var audioVideo2 = AudioVideoMedia.with(expectedChecksum, "AudioVideo2", expectedRawLocation, expectedEncodedLocation, expectedStatus);

        // then
        assertEquals(audioVideo1, audioVideo2);
        assertNotSame(audioVideo1, audioVideo2);
    }

    @Test
    public void givenInvalidParams_whenCallsNewImage_shouldReturnError() {
        assertThrows(NullPointerException.class, () -> AudioVideoMedia.with(null, "banner.png", "/images/banner.png", "/images/banner.png", MediaStatus.PENDING));
        assertThrows(NullPointerException.class, () -> AudioVideoMedia.with("checksum", null, "/images/banner.png", "/images/banner.png", MediaStatus.PENDING));
        assertThrows(NullPointerException.class, () -> AudioVideoMedia.with("checksum", "banner.png", null, "/images/banner.png", MediaStatus.PENDING));
        assertThrows(NullPointerException.class, () -> AudioVideoMedia.with("checksum", "banner.png", "/images/banner.png", null, MediaStatus.PENDING));
        assertThrows(NullPointerException.class, () -> AudioVideoMedia.with("checksum", "banner.png", "/images/banner.png", "/images/banner.png", null));
        assertThrows(NullPointerException.class, () -> AudioVideoMedia.with(null, "banner.png", "/images/banner.png", "/images/banner.png", MediaStatus.PENDING));

    }



}
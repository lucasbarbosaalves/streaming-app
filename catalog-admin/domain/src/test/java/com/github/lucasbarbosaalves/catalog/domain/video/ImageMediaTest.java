package com.github.lucasbarbosaalves.catalog.domain.video;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImageMediaTest {

    @Test
    public void givenAValidParams_whenCallsNewImage_shouldReturnInstance() {
        // given
        final var expectedChecksum = "checksum";
        final var expectedName = "banner.png";
        final var expectedLocation = "/images/banner.png";

        // when
        final var actual = ImageMedia.with(expectedChecksum, expectedName, expectedLocation);

        assertNotNull(actual);
        assertEquals(expectedChecksum, actual.checksum());
        assertEquals(expectedName, actual.name());
        assertEquals(expectedLocation, actual.location());

    }

    @Test
    public void givenSamesValues_whenCallsEquals_shouldReturnTrue() {
        // given
        final var expectedChecksum = "checksum";
        final var expectedLocation = "/images/banner.png";

        final var image1 = ImageMedia.with(expectedChecksum, "Image1", expectedLocation);
        final var image2 = ImageMedia.with(expectedChecksum, "Image2", expectedLocation);

        // then
        assertEquals(image1, image2);
        assertNotSame(image1, image2);
    }

    @Test
    public void givenInvalidParams_whenCallsNewImage_shouldReturnError() {
        assertThrows(NullPointerException.class, () -> ImageMedia.with(null, "banner.png", "/images/banner.png"));
        assertThrows(NullPointerException.class, () -> ImageMedia.with("checksum", null, "/images/banner.png"));
        assertThrows(NullPointerException.class, () -> ImageMedia.with("checksum", "banner.png", null));
    }
}
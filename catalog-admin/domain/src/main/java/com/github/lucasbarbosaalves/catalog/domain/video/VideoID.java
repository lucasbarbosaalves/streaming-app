package com.github.lucasbarbosaalves.catalog.domain.video;

import com.github.lucasbarbosaalves.catalog.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class VideoID extends Identifier {
    private final String value;

    private VideoID(final String value) {
        this.value = Objects.requireNonNull(value);

    }

    public static VideoID unique() {
        return VideoID.from(UUID.randomUUID());
    }

    public static VideoID from(final String value) {
        return new VideoID(value.toLowerCase());
    }

    public static VideoID from(final UUID value) {
        return from(value.toString());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final VideoID that = (VideoID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}



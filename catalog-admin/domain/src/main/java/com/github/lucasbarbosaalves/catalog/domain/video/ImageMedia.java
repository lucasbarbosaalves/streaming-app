package com.github.lucasbarbosaalves.catalog.domain.video;

import com.github.lucasbarbosaalves.catalog.domain.ValueObject;

import java.util.Objects;

public class ImageMedia extends ValueObject {

    private final String checksum;

    private final String name;

    private final String location;

    private ImageMedia(final String checksum, final String name, final String location) {
        this.checksum = Objects.requireNonNull(checksum);
        this.name = Objects.requireNonNull(name);
        this.location = Objects.requireNonNull(location);
    }

    public static ImageMedia with(final String checksum, final String name, final String location) {
        return new ImageMedia(checksum, name, location);
    }

    public String checksum() {
        return checksum;
    }

    public String name() {
        return name;
    }

    public String location() {
        return location;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        ImageMedia that = (ImageMedia) object;
        return Objects.equals(checksum, that.checksum) && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checksum, location);
    }
}

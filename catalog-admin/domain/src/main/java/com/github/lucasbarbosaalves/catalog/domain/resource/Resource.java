package com.github.lucasbarbosaalves.catalog.domain.resource;

import com.github.lucasbarbosaalves.catalog.domain.ValueObject;

import java.util.Objects;

/**
 * Represents a resource in the system, such as a video, trailer, banner, or thumbnail.
 * This class encapsulates the content of the resource, its content type, name, and type.
 */
public class Resource extends ValueObject {

    private final String checksum;
    private final byte[] content;
    private final String contentType;
    private final String name;

    private Resource(String checksum, final byte[] content, final String contentType, final String name) {
        this.checksum = checksum;
        this.content = Objects.requireNonNull(content);
        this.contentType = Objects.requireNonNull(contentType);
        this.name = Objects.requireNonNull(name);
    }

    public static Resource with(final String checksum, final byte[] content, final String contentType, final String name) {
        return new Resource(checksum, content, contentType, name);
    }

    public String checksum() {
        return checksum;
    }
    public byte[] content() {
        return content;
    }

    public String contentType() {
        return contentType;
    }

    public String name() {
        return name;
    }

}

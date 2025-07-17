package com.github.lucasbarbosaalves.catalog.domain.video;

import com.github.lucasbarbosaalves.catalog.domain.ValueObject;
import com.github.lucasbarbosaalves.catalog.domain.utils.IdUtils;

import java.util.Objects;

public class AudioVideoMedia extends ValueObject {

    private final String id;
    private final String checksum; // Hash Function -> representação do binário do arquivo
    private final String name;
    private final String rawLocation; // Localização do arquivo no sistema de arquivos
    private final String encodedLocation; // Localização do arquivo no sistema de arquivos após o processo de codificação
    private final MediaStatus status; // Status do arquivo (PENDING, PROCESSING, READY, FAILED)

    private AudioVideoMedia(
            final String id,
            final String checksum,
            final String name,
            final String rawLocation,
            final String encodedLocation,
            final MediaStatus status
    ) {
        this.id = Objects.requireNonNull(id);
        this.checksum = Objects.requireNonNull(checksum);
        this.name = Objects.requireNonNull(name);
        this.rawLocation = Objects.requireNonNull(rawLocation);
        this.encodedLocation = Objects.requireNonNull(encodedLocation);
        this.status = Objects.requireNonNull(status);
    }

    public static AudioVideoMedia with(
            final String checksum,
            final String name,
            final String rawLocation
    ) {
        return new AudioVideoMedia(IdUtils.uuid(), checksum, name, rawLocation, "", MediaStatus.PENDING);
    }

    public static AudioVideoMedia with(
            final String id,
            final String checksum,
            final String name,
            final String rawLocation,
            final String encodedLocation,
            final MediaStatus status
    ) {
        return new AudioVideoMedia(id, checksum, name, rawLocation, encodedLocation, status);
    }

    public String id() {
        return id;
    }

    public String checksum() {
        return checksum;
    }

    public String name() {
        return name;
    }

    public String rawLocation() {
        return rawLocation;
    }

    public String encodedLocation() {
        return encodedLocation;
    }

    public MediaStatus status() {
        return status;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        AudioVideoMedia that = (AudioVideoMedia) object;
        return Objects.equals(checksum, that.checksum) && Objects.equals(rawLocation, that.rawLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checksum, rawLocation);
    }
}

package com.github.lucasbarbosaalves.catalog.infrastructure.video.persistence;

import com.github.lucasbarbosaalves.catalog.domain.video.AudioVideoMedia;
import com.github.lucasbarbosaalves.catalog.domain.video.MediaStatus;
import jakarta.persistence.*;

@Entity(name = "AudioVideoMedia")
@Table(name = "videos_video_media")
public class AudioVideoMediaJpaEntity {

    @Id
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "encoded_path", nullable = false)
    private String encodedPath;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MediaStatus status;


    public AudioVideoMediaJpaEntity() {
    }

    private AudioVideoMediaJpaEntity(final String id, final String name, final String filePath, final String encodedPath, final MediaStatus status) {
        this.id = id;
        this.name = name;
        this.filePath = filePath;
        this.encodedPath = encodedPath;
        this.status = status;
    }

    public static AudioVideoMediaJpaEntity from(final AudioVideoMedia audioVideoMedia) {
        return new AudioVideoMediaJpaEntity(
                audioVideoMedia.checksum(),
                audioVideoMedia.name(),
                audioVideoMedia.rawLocation(),
                audioVideoMedia.encodedLocation(),
                audioVideoMedia.status()
        );
    }

    public AudioVideoMedia toDomain() {
        return AudioVideoMedia.with(
                getId(),
                getName(),
                getFilePath(),
                getEncodedPath(),
                getStatus()
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getEncodedPath() {
        return encodedPath;
    }

    public void setEncodedPath(String encodedPath) {
        this.encodedPath = encodedPath;
    }

    public MediaStatus getStatus() {
        return status;
    }

    public void setStatus(MediaStatus status) {
        this.status = status;
    }
}

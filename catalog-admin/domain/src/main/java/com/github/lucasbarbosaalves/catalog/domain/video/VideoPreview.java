package com.github.lucasbarbosaalves.catalog.domain.video;

import java.time.Instant;

/**
 * Represents a preview of a video in the catalog.
 * This record contains essential information about the video,
 * such as its ID, title, description, and timestamps for creation and updates.
 */
public record VideoPreview(
        String id,
        String title,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
    public VideoPreview(final Video video) {
        this(
                video.getId().getValue(),
                video.getTitle(),
                video.getDescription(),
                video.getCreatedAt(),
                video.getUpdatedAt()
        );
    }
}

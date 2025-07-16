package com.github.lucasbarbosaalves.catalog.application.video.retrieve.list;

import com.github.lucasbarbosaalves.catalog.domain.video.Video;
import com.github.lucasbarbosaalves.catalog.domain.video.VideoPreview;

import java.time.Instant;

public record VideoListOutput(
        String id,
        String title,
        String description,
        Instant createdAt,
        Instant updatedAt
) {

    public static VideoListOutput from(final Video video) {
        return new VideoListOutput(
                video.getId().getValue(),
                video.getTitle(),
                video.getDescription(),
                video.getCreatedAt(),
                video.getUpdatedAt()
        );
    }

    public static VideoListOutput from(final VideoPreview preview) {
        return new VideoListOutput(
                preview.id(),
                preview.title(),
                preview.description(),
                preview.createdAt(),
                preview.updatedAt()
        );
    }

}

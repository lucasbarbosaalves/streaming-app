package com.github.lucasbarbosaalves.catalog.application.video.create;

import com.github.lucasbarbosaalves.catalog.domain.video.Resource;

import java.util.Optional;
import java.util.Set;

public record CreateVideoCommand(
        String title,
        String description,
        Integer launchedAt,
        Double duration,
        Boolean opened,
        Boolean published,
        String rating,
        Set<String> categories,
        Set<String> genres,
        Set<String> castMembers,
        Resource video,
        Resource trailer,
        Resource banner,
        Resource thumbnail,
        Resource thumbnailHalf
) {

    public static CreateVideoCommand with(
            final String title,
            final String description,
            final int launchedAt,
            final double duration,
            final boolean opened,
            final boolean published,
            final String rating,
            final Set<String> categories,
            final Set<String> genres,
            final Set<String> castMembers,
            final Resource video,
            final Resource trailer,
            final Resource banner,
            final Resource thumbnail,
            final Resource thumbnailHalf
    ) {
        return new CreateVideoCommand(
                title, description, launchedAt, duration, opened, published, rating, categories, genres, castMembers, video, trailer, banner, thumbnail, thumbnailHalf
        );
    }

    public Optional<Resource> getVideo() {
        return Optional.ofNullable(video);
    }

    public Optional<Resource> getTrailer() {
        return Optional.ofNullable(trailer);
    }

    public Optional<Resource> getBanner() {
        return Optional.ofNullable(banner);
    }

    public Optional<Resource> getThumbnail() {
        return Optional.ofNullable(thumbnail);
    }

    public Optional<Resource> getThumbnailHalf() {
        return Optional.ofNullable(thumbnailHalf);
    }
}

package com.github.lucasbarbosaalves.catalog.application.video.retrieve.get;

import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberID;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import com.github.lucasbarbosaalves.catalog.domain.genre.GenreID;
import com.github.lucasbarbosaalves.catalog.domain.utils.CollectionUtils;
import com.github.lucasbarbosaalves.catalog.domain.video.AudioVideoMedia;
import com.github.lucasbarbosaalves.catalog.domain.video.ImageMedia;
import com.github.lucasbarbosaalves.catalog.domain.video.Video;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

public record VideoOutput (
        String id,
        String title,
        String description,
        int launchYear,
        double duration,
        boolean opened,
        boolean published,
        String rating,
        Set<String> categories,
        Set<String> genres,
        Set<String> members,
        ImageMedia banner,
        ImageMedia thumbnail,
        ImageMedia thumbnailHalf,
        AudioVideoMedia video,
        AudioVideoMedia trailer,
        Instant createdAt,
        Instant updatedAt
) {

    public static VideoOutput from(Video video) {
        return new VideoOutput(
                video.getId().getValue(),
                video.getTitle(),
                video.getDescription(),
                video.getLaunchedAt().getValue(),
                video.getDuration(),
                video.getOpened(),
                video.getPublished(),
                video.getRating().getName(),
                CollectionUtils.mapTo(video.getCategories(), CategoryID::getValue),
                CollectionUtils.mapTo(video.getGenres(), GenreID::getValue),
                CollectionUtils.mapTo(video.getCastMembers(), CastMemberID::getValue),
                video.getBanner().orElse(null),
                video.getThumbnail().orElse(null),
                video.getThumbnailHalf().orElse(null),
                video.getVideo().orElse(null),
                video.getTrailer().orElse(null),
                video.getCreatedAt(),
                video.getUpdatedAt()
        );
    }
}

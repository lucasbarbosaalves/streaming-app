package com.github.lucasbarbosaalves.catalog.application.video.create;

import com.github.lucasbarbosaalves.catalog.domain.Identifier;
import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberGateway;
import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberID;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryGateway;
import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import com.github.lucasbarbosaalves.catalog.domain.exception.InternalErrorException;
import com.github.lucasbarbosaalves.catalog.domain.exception.NotificationException;
import com.github.lucasbarbosaalves.catalog.domain.genre.GenreGateway;
import com.github.lucasbarbosaalves.catalog.domain.genre.GenreID;
import com.github.lucasbarbosaalves.catalog.domain.validation.Error;
import com.github.lucasbarbosaalves.catalog.domain.validation.ValidationHandler;
import com.github.lucasbarbosaalves.catalog.domain.validation.handler.Notification;
import com.github.lucasbarbosaalves.catalog.domain.video.MediaResourceGateway;
import com.github.lucasbarbosaalves.catalog.domain.video.Rating;
import com.github.lucasbarbosaalves.catalog.domain.video.Video;
import com.github.lucasbarbosaalves.catalog.domain.video.VideoGateway;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DefaultCreateVideoUseCase extends CreateVideoUseCase {

    private final CategoryGateway categoryGateway;
    private final CastMemberGateway castMemberGateway;
    private final GenreGateway genreGateway;
    private final MediaResourceGateway mediaResourceGateway;
    private final VideoGateway videoGateway;

    public DefaultCreateVideoUseCase(
            final CategoryGateway categoryGateway,
            final CastMemberGateway castMemberGateway,
            final GenreGateway genreGateway,
            final MediaResourceGateway mediaResourceGateway,
            final VideoGateway videoGateway
    ) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
        this.mediaResourceGateway = Objects.requireNonNull(mediaResourceGateway);
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public CreateVideoOutput execute(final CreateVideoCommand aCommand) {
        final var aRating = Rating.of(aCommand.rating()).orElse(null);
        final var aLaunchYear = aCommand.launchedAt() != null ? Year.of(aCommand.launchedAt()) : null;
        final var categories = toIdentifier(aCommand.categories(), CategoryID::from);
        final var genres = toIdentifier(aCommand.genres(), GenreID::from);
        final var members = toIdentifier(aCommand.castMembers(), CastMemberID::from);

        final var notification = Notification.create();
        notification.append(validateCategories(categories));
        notification.append(validateGenres(genres));
        notification.append(validateMembers(members));

        if (notification.hasError()) {
            throw new NotificationException("Could not create Aggregate Video", notification);
        }

        final var aVideo = Video.newVideo(
                aCommand.title(),
                aCommand.description(),
                aLaunchYear,
                aCommand.duration(),
                aCommand.opened(),
                aCommand.published(),
                aRating,
                categories,
                genres,
                members
        );

        aVideo.validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Could not create Aggregate Video", notification);
        }

        return CreateVideoOutput.from(create(aCommand, aVideo));
    }

    private Video create(final CreateVideoCommand aCommand, final Video aVideo) {
        final var anId = aVideo.getId();

        try {
            final var aVideoMedia = aCommand.getVideo()
                    .map(it -> this.mediaResourceGateway.storeAudioVideo(anId, it))
                    .orElse(null);

            final var aTrailerMedia = aCommand.getTrailer()
                    .map(it -> this.mediaResourceGateway.storeAudioVideo(anId, it))
                    .orElse(null);

            final var aBannerMedia = aCommand.getBanner()
                    .map(it -> this.mediaResourceGateway.storeImage(anId, it))
                    .orElse(null);

            final var aThumbnailMedia = aCommand.getThumbnail()
                    .map(it -> this.mediaResourceGateway.storeImage(anId, it))
                    .orElse(null);

            final var aThumbHalfMedia = aCommand.getThumbnailHalf()
                    .map(it -> this.mediaResourceGateway.storeImage(anId, it))
                    .orElse(null);

            return this.videoGateway.create(
                    aVideo
                            .setVideo(aVideoMedia)
                            .setTrailer(aTrailerMedia)
                            .setBanner(aBannerMedia)
                            .setThumbnail(aThumbnailMedia)
                            .setThumbnailHalf(aThumbHalfMedia)
            );
        } catch (final Throwable t) {
            this.mediaResourceGateway.clearResources(anId);
            throw InternalErrorException.with(
                    "An error on create video was observed [videoId:%s]".formatted(anId.getValue()),
                    t
            );
        }
    }

    private ValidationHandler validateCategories(final Set<CategoryID> ids) {
        return validateAggregate("categories", ids, categoryGateway::existsByIds);
    }

    private ValidationHandler validateGenres(final Set<GenreID> ids) {
        return validateAggregate("genres", ids, genreGateway::existsByIds);
    }

    private ValidationHandler validateMembers(final Set<CastMemberID> ids) {
        return validateAggregate("cast members", ids, castMemberGateway::existsByIds);
    }

    private <T extends Identifier> ValidationHandler validateAggregate(
            final String aggregate,
            final Set<T> ids,
            final Function<Iterable<T>, List<T>> existsByIds
    ) {
        final var notification = Notification.create();
        if (ids == null || ids.isEmpty()) {
            return notification;
        }

        final var retrievedIds = existsByIds.apply(ids);

        if (ids.size() != retrievedIds.size()) {
            final var missingIds = new ArrayList<>(ids);
            missingIds.removeAll(retrievedIds);

            final var missingIdsMessage = missingIds.stream()
                    .map(Identifier::getValue)
                    .collect(Collectors.joining(", "));

            notification.append(new Error("Some %s could not be found: %s".formatted(aggregate, missingIdsMessage)));
        }

        return notification;
    }

    private <T> Set<T> toIdentifier(final Set<String> ids, final Function<String, T> mapper) {
        return ids.stream()
                .map(mapper)
                .collect(Collectors.toSet());
    }
}
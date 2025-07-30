package com.github.lucasbarbosaalves.catalog.application.video.media.get;

import com.github.lucasbarbosaalves.catalog.domain.category.NotFoundException;
import com.github.lucasbarbosaalves.catalog.domain.validation.Error;
import com.github.lucasbarbosaalves.catalog.domain.video.MediaResourceGateway;
import com.github.lucasbarbosaalves.catalog.domain.video.VideoID;
import com.github.lucasbarbosaalves.catalog.domain.video.VideoMediaType;

import java.util.Objects;

public class DefaultGetMediaUseCase extends GetMediaUseCase {

    private final MediaResourceGateway mediaResourceGateway;

    public DefaultGetMediaUseCase(final MediaResourceGateway mediaResourceGateway) {
        this.mediaResourceGateway = Objects.requireNonNull(mediaResourceGateway);
    }

    @Override
    public MediaOutput execute(final GetMediaCommand command) {
        final var id = VideoID.from(command.videoId());
        final var type = VideoMediaType.of(command.mediaType())
                .orElseThrow(() -> typeNotFound(command.mediaType()));

        final var resource = this.mediaResourceGateway.getResource(id, type)
                .orElseThrow(() -> notFound(id, type));

        return MediaOutput.with(resource);
    }

    private NotFoundException notFound(final VideoID id, final VideoMediaType type) {
        return NotFoundException.with(new Error("Resource %s not found for video %s".formatted(id, type)));
    }

    private NotFoundException typeNotFound(final String type) {
        return NotFoundException.with(new Error("Media type %s doesn't exists".formatted(type)));
    }
}

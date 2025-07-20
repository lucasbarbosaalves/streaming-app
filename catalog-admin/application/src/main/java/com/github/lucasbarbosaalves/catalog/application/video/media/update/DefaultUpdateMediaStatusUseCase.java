package com.github.lucasbarbosaalves.catalog.application.video.media.update;

import com.github.lucasbarbosaalves.catalog.domain.category.NotFoundException;
import com.github.lucasbarbosaalves.catalog.domain.video.*;

import java.util.Objects;

public class DefaultUpdateMediaStatusUseCase extends UpdateMediaStatusUseCase {

    private final VideoGateway videoGateway;

    public DefaultUpdateMediaStatusUseCase(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }


    @Override
    public void execute(final UpdateMediaStatusCommand command) {
        final var id = VideoID.from(command.videoId());
        final var resourceId = command.resourceId();
        final var folder = command.folderLocation();
        final var fileName = command.fileName();

        final var video = this.videoGateway.findById(id)
                .orElseThrow(() -> notFound(id));

        String encodedPath = "%s/%s".formatted(folder, fileName);

        if (matches(resourceId, video.getVideo().orElse(null))) {

            updateVideo(VideoMediaType.VIDEO, command.status(), video, encodedPath);
            this.videoGateway.update(video);
        } else if (matches(resourceId, video.getTrailer().orElse(null))) {
            updateVideo(VideoMediaType.TRAILER, command.status(), video, encodedPath);
            this.videoGateway.update(video);
        }

    }

    private static void updateVideo(final VideoMediaType type, final MediaStatus status, final Video video, final String encodedPath) {
        switch (status) {
            case PENDING -> {
            }
            case PROCESSING -> video.processing(type);
            case READY -> video.completed(type, encodedPath);
        }
    }

    private boolean matches(String id, final AudioVideoMedia media) {
        if (media == null) {
            return false;
        }

        return media.id().equals(id);
    }

    private NotFoundException notFound(VideoID id) {
        return NotFoundException.with(Video.class, id);
    }
}

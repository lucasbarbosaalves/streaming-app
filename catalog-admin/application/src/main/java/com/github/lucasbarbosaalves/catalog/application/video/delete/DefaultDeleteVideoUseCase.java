package com.github.lucasbarbosaalves.catalog.application.video.delete;

import com.github.lucasbarbosaalves.catalog.domain.video.MediaResourceGateway;
import com.github.lucasbarbosaalves.catalog.domain.video.VideoGateway;
import com.github.lucasbarbosaalves.catalog.domain.video.VideoID;

import java.util.Objects;

public class DefaultDeleteVideoUseCase extends DeleteVideoUseCase{

    private final VideoGateway videoGateway;
    private final MediaResourceGateway mediaResourceGateway;

    public DefaultDeleteVideoUseCase(final VideoGateway videoGateway, final MediaResourceGateway mediaResourceGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
        this.mediaResourceGateway = Objects.requireNonNull(mediaResourceGateway);
    }

    @Override
    public void execute(final String id) {
        final var videoId = VideoID.from(id);
        this.videoGateway.deleteById(videoId);
        this.mediaResourceGateway.clearResources(videoId);
    }
}

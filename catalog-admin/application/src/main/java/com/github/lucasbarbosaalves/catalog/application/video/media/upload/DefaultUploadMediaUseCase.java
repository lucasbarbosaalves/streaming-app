package com.github.lucasbarbosaalves.catalog.application.video.media.upload;

import com.github.lucasbarbosaalves.catalog.domain.category.NotFoundException;
import com.github.lucasbarbosaalves.catalog.domain.video.MediaResourceGateway;
import com.github.lucasbarbosaalves.catalog.domain.video.Video;
import com.github.lucasbarbosaalves.catalog.domain.video.VideoGateway;
import com.github.lucasbarbosaalves.catalog.domain.video.VideoID;

import java.util.Objects;

public class DefaultUploadMediaUseCase extends UploadMediaUseCase {

    private final VideoGateway videoGateway;
    private final MediaResourceGateway mediaResourceGateway;


    public DefaultUploadMediaUseCase(final VideoGateway videoGateway, final MediaResourceGateway mediaResourceGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
        this.mediaResourceGateway = Objects.requireNonNull(mediaResourceGateway);
    }

    @Override
    public UploadMediaOutput execute(UploadMediaCommand command) {
        final var id = VideoID.from(command.videoId());
        final var resource = command.videoResource();

        final var video = this.videoGateway.findById(id)
                .orElseThrow(() -> notFound(id));

        switch (resource.type()) {
            case VIDEO -> video.setVideo(mediaResourceGateway.storeAudioVideo(id, resource));
            case TRAILER -> video.setTrailer(mediaResourceGateway.storeAudioVideo(id, resource));
            case BANNER -> video.setBanner(mediaResourceGateway.storeImage(id, resource));
            case THUMBNAIL -> video.setThumbnail(mediaResourceGateway.storeImage(id, resource));
            case THUMBNAIL_HALF -> video.setThumbnailHalf(mediaResourceGateway.storeImage(id, resource));
        }

        return UploadMediaOutput.with(videoGateway.update(video), resource.type());
    }

    private NotFoundException notFound(VideoID id) {
        return NotFoundException.with(Video.class, id);
    }
}

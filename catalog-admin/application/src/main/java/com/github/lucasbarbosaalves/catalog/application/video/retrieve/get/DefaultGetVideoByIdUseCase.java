package com.github.lucasbarbosaalves.catalog.application.video.retrieve.get;

import com.github.lucasbarbosaalves.catalog.domain.category.NotFoundException;
import com.github.lucasbarbosaalves.catalog.domain.video.Video;
import com.github.lucasbarbosaalves.catalog.domain.video.VideoGateway;
import com.github.lucasbarbosaalves.catalog.domain.video.VideoID;

import java.util.Objects;

public class DefaultGetVideoByIdUseCase extends GetVideoByIdUseCase{

    private final VideoGateway videoGateway;

    public DefaultGetVideoByIdUseCase(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public VideoOutput execute(String param) {
        VideoID id = VideoID.from(param);
        return this.videoGateway.findById(id)
                .map(VideoOutput::from)
                .orElseThrow(() -> NotFoundException.with(Video.class, id));
    }
}

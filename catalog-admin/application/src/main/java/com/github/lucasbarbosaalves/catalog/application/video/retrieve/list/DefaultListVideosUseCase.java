package com.github.lucasbarbosaalves.catalog.application.video.retrieve.list;

import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;
import com.github.lucasbarbosaalves.catalog.domain.video.VideoGateway;
import com.github.lucasbarbosaalves.catalog.domain.video.VideoSearchQuery;

import java.util.Objects;

public class DefaultListVideosUseCase extends ListVideoUseCase {

    private final VideoGateway videoGateway;

    public DefaultListVideosUseCase(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public Pagination<VideoListOutput> execute(VideoSearchQuery query) {
        return this.videoGateway.findAll(query)
                .map(VideoListOutput::from);
    }
}

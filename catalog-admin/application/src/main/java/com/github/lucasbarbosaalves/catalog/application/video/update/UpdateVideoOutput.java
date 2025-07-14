package com.github.lucasbarbosaalves.catalog.application.video.update;

import com.github.lucasbarbosaalves.catalog.domain.video.Video;

public record UpdateVideoOutput(String id) {
    public static UpdateVideoOutput from(final Video id) {
        return new UpdateVideoOutput(id.getId().getValue());
    }
}

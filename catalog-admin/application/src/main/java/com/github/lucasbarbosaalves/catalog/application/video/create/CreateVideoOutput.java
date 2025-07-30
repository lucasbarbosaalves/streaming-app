package com.github.lucasbarbosaalves.catalog.application.video.create;

import com.github.lucasbarbosaalves.catalog.domain.video.Video;

public record CreateVideoOutput(String id) {
    public static CreateVideoOutput from(final Video id) {
        return new CreateVideoOutput(id.getId().getValue());
    }
}

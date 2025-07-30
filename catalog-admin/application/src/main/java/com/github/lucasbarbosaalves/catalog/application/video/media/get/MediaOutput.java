package com.github.lucasbarbosaalves.catalog.application.video.media.get;

import com.github.lucasbarbosaalves.catalog.domain.resource.Resource;

public record MediaOutput(byte[] content, String contentType, String name) {

    public static MediaOutput with(final Resource resource) {
        return new MediaOutput(resource.content(), resource.contentType(), resource.name());
    }
}

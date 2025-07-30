package com.github.lucasbarbosaalves.catalog.application.video.media.upload;

import com.github.lucasbarbosaalves.catalog.domain.video.VideoResource;

public record UploadMediaCommand(String videoId, VideoResource videoResource) {

    public static UploadMediaCommand with(final String videoId, final VideoResource videoResource) {
        return new UploadMediaCommand(videoId, videoResource);
    }
}

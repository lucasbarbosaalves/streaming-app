package com.github.lucasbarbosaalves.catalog.application.video.media.update;

import com.github.lucasbarbosaalves.catalog.domain.video.MediaStatus;

public record UpdateMediaStatusCommand(MediaStatus status, String videoId, String resourceId, String folderLocation,
                                       String fileName) {

    public static UpdateMediaStatusCommand with(
            final MediaStatus status,
            final String videoId,
            final String resourceId,
            final String folderLocation,
            final String fileName
    ) {
        return new UpdateMediaStatusCommand(status, videoId, resourceId, folderLocation, fileName);
    }
}

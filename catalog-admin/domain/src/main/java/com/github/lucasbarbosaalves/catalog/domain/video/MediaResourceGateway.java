package com.github.lucasbarbosaalves.catalog.domain.video;

import com.github.lucasbarbosaalves.catalog.domain.resource.Resource;

import java.util.Optional;

public interface MediaResourceGateway {

    AudioVideoMedia storeAudioVideo(VideoID id, VideoResource resource);

    ImageMedia storeImage(VideoID id, VideoResource resource);

    void clearResources(VideoID id);

    Optional<Resource> getResource(VideoID id, VideoMediaType type);
}

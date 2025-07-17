package com.github.lucasbarbosaalves.catalog.domain.video;

public interface MediaResourceGateway {

    AudioVideoMedia storeAudioVideo(VideoID id, VideoResource resource);

    ImageMedia storeImage(VideoID id, VideoResource resource);

    void clearResources(VideoID id);
}

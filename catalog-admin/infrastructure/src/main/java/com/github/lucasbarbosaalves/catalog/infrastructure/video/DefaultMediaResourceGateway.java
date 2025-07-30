package com.github.lucasbarbosaalves.catalog.infrastructure.video;

import com.github.lucasbarbosaalves.catalog.domain.resource.Resource;
import com.github.lucasbarbosaalves.catalog.domain.video.*;
import com.github.lucasbarbosaalves.catalog.infrastructure.config.properties.storage.StorageProperties;
import com.github.lucasbarbosaalves.catalog.infrastructure.services.StorageService;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementation
 */
@Component
public class DefaultMediaResourceGateway implements MediaResourceGateway {

    private final String filenamePattern;
    private final String locationPattern;
    private final StorageService storageService;

    public DefaultMediaResourceGateway(StorageProperties props, StorageService storageService) {
        this.filenamePattern = props.getFilenamePattern();
        this.locationPattern = props.getLocationPattern();
        this.storageService = storageService;
    }

    @Override
    public AudioVideoMedia storeAudioVideo(VideoID id, VideoResource videoResource) {
        final var filePath = filePath(id, videoResource.type());
        Resource resource = videoResource.resource();
        store(filePath, resource);
        return AudioVideoMedia.with(resource.checksum(), resource.name(), filePath);
    }

    @Override
    public ImageMedia storeImage(VideoID id, VideoResource videoResource) {
        final var filePath = filePath(id, videoResource.type());
        Resource resource = videoResource.resource();
        store(filePath, resource);
        return ImageMedia.with(resource.checksum(), resource.name(), filePath);
    }

    @Override
    public void clearResources(VideoID id) {
        final var ids = this.storageService.list(folder(id));
        this.storageService.deleteAll(ids);
    }

    @Override
    public Optional<Resource> getResource(VideoID id, VideoMediaType type) {
        return this.storageService.get(filePath(id, type));
    }

    private String filename(VideoMediaType type) {
        return filenamePattern.replace("{type}", type.name());
    }

    private String folder(VideoID id) {
        return locationPattern.replace("{videoId}", id.getValue());
    }

    private String filePath(VideoID id, VideoMediaType type) {
        return folder(id).concat("/").concat(filename(type));
    }

    private void store(String filePath, Resource resource) {
        this.storageService.store(filePath, resource);
    }
}

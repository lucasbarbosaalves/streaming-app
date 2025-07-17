package com.github.lucasbarbosaalves.catalog.infrastructure.services.impl;

import com.github.lucasbarbosaalves.catalog.domain.resource.Resource;
import com.github.lucasbarbosaalves.catalog.infrastructure.services.StorageService;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class GoogleCloudStorage implements StorageService {

    private final String bucket;
    private final Storage storage;

    public GoogleCloudStorage(final String bucket, final Storage storage) {
        this.bucket = bucket;
        this.storage = storage;
    }

    @Override
    public void store(String name, Resource resource) {
        final var blobInfo = BlobInfo.newBuilder(this.bucket, name)
                .setContentType(resource.contentType())
                .setCrc32cFromHexString("")
                .build();
        this.storage.create(blobInfo, resource.content());
    }

    @Override
    public void deleteAll(Collection<String> names) {
        List<BlobId> blobIds = names.stream()
                .map(name -> BlobId.of(this.bucket, name))
                .toList();
        this.storage.delete(blobIds);
    }

    @Override
    public List<String> list(String prefix) {
        final var blobs = this.storage.list(this.bucket, Storage.BlobListOption.prefix(prefix));
        return StreamSupport.stream(blobs.iterateAll().spliterator(), false)
                .map(BlobInfo::getBlobId)
                .map(BlobId::getName)
                .toList();
    }

    @Override
    public Optional<Resource> get(String name) {
        return Optional.ofNullable(this.storage.get(this.bucket, name))
                .map(blob -> Resource.with(
                        null,
                        blob.getContent(),
                        blob.getContentType(),
                        name
                ));
    }
}

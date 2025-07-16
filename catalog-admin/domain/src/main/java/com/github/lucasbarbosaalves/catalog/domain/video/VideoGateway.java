package com.github.lucasbarbosaalves.catalog.domain.video;

import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;

import java.util.Optional;

public interface VideoGateway {

    Video create(Video aVideo);

    Optional<Video> findById(VideoID anId);

    void deleteById(VideoID anId);

    Video update(Video aVideo);

    Pagination<VideoPreview> findAll(VideoSearchQuery aQuery);

}

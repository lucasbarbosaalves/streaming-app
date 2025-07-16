package com.github.lucasbarbosaalves.catalog.infrastructure.video;

import com.github.lucasbarbosaalves.catalog.domain.Identifier;
import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;
import com.github.lucasbarbosaalves.catalog.domain.utils.CollectionUtils;
import com.github.lucasbarbosaalves.catalog.domain.utils.SqlUtils;
import com.github.lucasbarbosaalves.catalog.domain.video.*;
import com.github.lucasbarbosaalves.catalog.infrastructure.video.persistence.VideoJpaEntity;
import com.github.lucasbarbosaalves.catalog.infrastructure.video.persistence.repository.VideoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static com.github.lucasbarbosaalves.catalog.domain.utils.CollectionUtils.mapTo;

public class DefaultVideoGateway implements VideoGateway {

    private final VideoRepository videoRepository;

    public DefaultVideoGateway(final VideoRepository videoRepository) {
        this.videoRepository = Objects.requireNonNull(videoRepository);
    }

    @Override
    @Transactional
    public Video create(final Video aVideo) {
        return save(aVideo);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Video> findById(final VideoID anId) {
        return this.videoRepository.findById(anId.getValue())
                .map(VideoJpaEntity::toAggregate);
    }

    @Override
    public void deleteById(final VideoID anId) {
        final var id = anId.getValue();
        if (this.videoRepository.existsById(id)) {
            this.videoRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public Video update(final Video aVideo) {
        return save(aVideo);
    }

    @Override
    public Pagination<VideoPreview> findAll(final VideoSearchQuery query) {

        PageRequest page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var actualPage = this.videoRepository.findAll(
                SqlUtils.like(query.terms()),
                CollectionUtils.nullIfEmpty(mapTo(query.castMembers(), Identifier::getValue)),
                CollectionUtils.nullIfEmpty(mapTo(query.categories(), Identifier::getValue)),
                CollectionUtils.nullIfEmpty(mapTo(query.genres(), Identifier::getValue)),
                page
        );


        return new Pagination<>(
                actualPage.getNumber(),
                actualPage.getSize(),
                actualPage.getTotalElements(),
                actualPage.toList()
        );
    }


    private Video save(final Video aVideo) {
        return this.videoRepository.save(VideoJpaEntity.from(aVideo)).toAggregate();
    }
}

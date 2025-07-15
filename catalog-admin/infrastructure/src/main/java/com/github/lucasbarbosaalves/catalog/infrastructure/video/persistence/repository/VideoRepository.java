package com.github.lucasbarbosaalves.catalog.infrastructure.video.persistence.repository;

import com.github.lucasbarbosaalves.catalog.infrastructure.video.persistence.VideoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VideoRepository extends JpaRepository<VideoJpaEntity, UUID> {
}

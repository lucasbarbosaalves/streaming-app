package com.github.lucasbarbosaalves.catalog.infrastructure.video.persistence.repository;

import com.github.lucasbarbosaalves.catalog.domain.video.VideoPreview;
import com.github.lucasbarbosaalves.catalog.infrastructure.video.persistence.VideoJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;


public interface VideoRepository extends JpaRepository<VideoJpaEntity, String> {

    @Query("""
            select new com.github.lucasbarbosaalves.catalog.domain.video.VideoPreview(
                v.id,
                v.title,
                v.description,
                v.createdAt,
                v.updatedAt
            )
            from Video v
                join v.castMembers members
                join v.categories categories
                join v.genres genres
            where\s
                (:terms is null or UPPER(v.title) like :terms)
            and (:castMembers is null or members.id.castMemberId in :castMembers)
            and (:categories is null or categories.id.categoryId in :categories)
            and (:genres is null or genres.id.genreId in :genres)""")
    Page<VideoPreview> findAll(
            @Param("terms") String terms,
            @Param("castmembers") Set<String> castMembers,
            @Param("categories") Set<String> categories,
            @Param("genres") Set<String> genres,
            Pageable page
    );
}

package com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryJpaEntity, String> {
    
    Page<CategoryJpaEntity> findAll(Specification<CategoryJpaEntity> spec, Pageable page);

    @Query(value = "select c.id from CategoryJpaEntity c where c.id in :ids")
    List<String> existsByIds(@Param("ids") List<String> ids);
}

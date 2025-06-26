package com.github.lucasbarbosaalves.catalog.infrastructure.category.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryJpaEntity, String> {
    
    Page<CategoryJpaEntity> findAll(Specification<CategoryJpaEntity> spec, Pageable page);
}

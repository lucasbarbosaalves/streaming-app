package com.github.lucasbarbosaalves.catalog.infrastructure.genre.persistence;

import com.github.lucasbarbosaalves.catalog.domain.category.CategoryID;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "genres_categories")
public class GenreCategoryJpaEntity {

    @EmbeddedId
    private GenreCategoryID id;

    @ManyToOne
    @MapsId("genreId")
    private GenreJpaEntity genre;

    public GenreCategoryJpaEntity() {}

    private GenreCategoryJpaEntity( final GenreJpaEntity genre,final CategoryID categoryId) {
        this.id = GenreCategoryID.from(genre.getId(), categoryId.getValue());
        this.genre = genre;
    }

    public static GenreCategoryJpaEntity from(final GenreJpaEntity genre, final CategoryID categoryId) {
        return new GenreCategoryJpaEntity(genre, categoryId);
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        GenreCategoryJpaEntity that = (GenreCategoryJpaEntity) object;
        return Objects.equals(id, that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public GenreCategoryID getId() {
        return id;
    }

    public void setId(GenreCategoryID id) {
        this.id = id;
    }

    public GenreJpaEntity getGenre() {
        return genre;
    }

    public void setGenre(GenreJpaEntity genre) {
        this.genre = genre;
    }
}

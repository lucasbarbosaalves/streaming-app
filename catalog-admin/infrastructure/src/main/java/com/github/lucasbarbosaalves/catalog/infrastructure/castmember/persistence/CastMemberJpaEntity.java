package com.github.lucasbarbosaalves.catalog.infrastructure.castmember.persistence;

import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMember;
import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberID;
import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberType;
import jakarta.persistence.*;

import java.time.Instant;

@Entity(name = "CastMember")
@Table(name = "cast_members")
public class CastMemberJpaEntity {

    @Id
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    // Por padrão, o JPA usa ordinal, mas é melhor usar STRING para evitar problemas com a ordem dos enums
    @Column(name = "type", nullable = false)
    private CastMemberType type;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(name = "updated_At", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    public CastMemberJpaEntity() {
    }

    public CastMemberJpaEntity(final String id, final String name, final CastMemberType type,
                               final Instant createdAt, final Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CastMemberJpaEntity from(final CastMember castMember) {
        return new CastMemberJpaEntity(
                castMember.getId().getValue(),
                castMember.getName(),
                castMember.getType(),
                castMember.getCreatedAt(),
                castMember.getUpdatedAt()
        );
    }

    public CastMember toAggregate() {
        return CastMember.with(
                CastMemberID.from(getId()),
                getName(),
                getType(),
                getCreatedAt(),
                getUpdatedAt()
        );
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public CastMemberType getType() {
        return type;
    }

    public void setType(final CastMemberType type) {
        this.type = type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}

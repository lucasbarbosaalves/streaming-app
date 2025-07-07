package com.github.lucasbarbosaalves.catalog.domain.castmember;

import com.github.lucasbarbosaalves.catalog.domain.AggregateRoot;
import com.github.lucasbarbosaalves.catalog.domain.category.Category;
import com.github.lucasbarbosaalves.catalog.domain.exception.NotificationException;
import com.github.lucasbarbosaalves.catalog.domain.utils.InstantUtils;
import com.github.lucasbarbosaalves.catalog.domain.validation.ValidationHandler;
import com.github.lucasbarbosaalves.catalog.domain.validation.handler.Notification;

import java.time.Instant;

public class CastMember extends AggregateRoot<CastMemberID> {

    private String name;
    private CastMemberType type;
    private Instant createdAt;
    private Instant updatedAt;

    private CastMember(final CastMemberID castMemberID,
                         final String name,
                         final CastMemberType type,
                         final Instant createdAt,
                         final Instant updatedAt) {
        super(castMemberID);
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        selfValidate();
    }

    public static CastMember newMember(final String name, final CastMemberType type) {
        final var id = CastMemberID.unique();
        final var now = InstantUtils.now();
        return new CastMember(id, name, type, now, now);
    }

    public static CastMember with(final CastMemberID id,
                                  final String name,
                                  final CastMemberType type,
                                  final Instant createdAt,
                                  final Instant updatedAt) {
        return new CastMember(id, name, type, createdAt, updatedAt);
    }

    //clone method
    public static CastMember with(final CastMember castMember) {
        return new CastMember(
                castMember.getId(),
                castMember.getName(),
                castMember.getType(),
                castMember.getCreatedAt(),
                castMember.getUpdatedAt()
        );
    }

    public CastMember update(final String name, final CastMemberType castMemberType) {
        this.name = name;
        this.type = castMemberType;
        this.updatedAt = InstantUtils.now();
        selfValidate();
        return this;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new CastMemberValidator(this, handler)
                .validate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CastMemberType getType() {
        return type;
    }

    public void setType(CastMemberType type) {
        this.type = type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Failed to create CastMember", notification);
        }
    }
}

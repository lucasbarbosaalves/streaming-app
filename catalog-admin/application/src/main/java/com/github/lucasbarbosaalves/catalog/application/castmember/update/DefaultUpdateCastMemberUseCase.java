package com.github.lucasbarbosaalves.catalog.application.castmember.update;

import com.github.lucasbarbosaalves.catalog.domain.Identifier;
import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMember;
import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberGateway;
import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberID;
import com.github.lucasbarbosaalves.catalog.domain.category.NotFoundException;
import com.github.lucasbarbosaalves.catalog.domain.exception.NotificationException;
import com.github.lucasbarbosaalves.catalog.domain.validation.handler.Notification;

import java.util.Objects;
import java.util.function.Supplier;

public non-sealed class DefaultUpdateCastMemberUseCase extends UpdateCastMemberUseCase {

    private final CastMemberGateway castMemberGateway;

    public DefaultUpdateCastMemberUseCase(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public UpdateCastMemberOutput execute(final UpdateCastMemberCommand command) {
        final var id = CastMemberID.from(command.id());
        final var name = command.name();
        final var type = command.type();

        final var member = this.castMemberGateway.findById(id)
                .orElseThrow(notFound(id));

        final var notification = Notification.create();
        notification.validate(() -> member.update(name, type));

        if (notification.hasError()) {
            notify(id, notification);
        }

        return UpdateCastMemberOutput.from(this.castMemberGateway.update(member));
    }

    private Supplier<NotFoundException> notFound(final CastMemberID id) {
        return () -> NotFoundException.with(CastMember.class, id);
    }

    private static void notify(final Identifier id, Notification notification) {
        throw new NotificationException("Could not update aggregate CastMember %s".formatted(id.getValue()), notification);
    }
}

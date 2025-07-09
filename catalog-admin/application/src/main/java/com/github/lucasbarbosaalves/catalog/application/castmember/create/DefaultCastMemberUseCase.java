package com.github.lucasbarbosaalves.catalog.application.castmember.create;

import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMember;
import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberGateway;
import com.github.lucasbarbosaalves.catalog.domain.exception.NotificationException;
import com.github.lucasbarbosaalves.catalog.domain.validation.handler.Notification;

import java.util.Objects;

public final class DefaultCastMemberUseCase extends CreateCastMemberUseCase {

    private final CastMemberGateway gateway;

    public DefaultCastMemberUseCase(CastMemberGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public CreateCastMemberOutput execute(CreateCastMemberCommand command) {
        final var name = command.name();
        final var type = command.type();
        
        final var notification = Notification.create();
        final var member = notification.validate(() -> CastMember.newMember(name, type));

        if (notification.hasError()) {
            notify(notification);
        }

        return CreateCastMemberOutput.from(this.gateway.create(member));
    }

    private static void notify(Notification notification) {
        throw new NotificationException("Could not create aggregate CastMember", notification);
    }
}

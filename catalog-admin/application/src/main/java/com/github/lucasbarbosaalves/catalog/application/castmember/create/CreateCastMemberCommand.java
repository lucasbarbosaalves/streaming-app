package com.github.lucasbarbosaalves.catalog.application.castmember.create;

import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberType;

public record CreateCastMemberCommand(
        String name,
        CastMemberType type
) {
    public static CreateCastMemberCommand with(
            final String name,
            final CastMemberType type
    ) {
        return new CreateCastMemberCommand(name, type);
    }
}

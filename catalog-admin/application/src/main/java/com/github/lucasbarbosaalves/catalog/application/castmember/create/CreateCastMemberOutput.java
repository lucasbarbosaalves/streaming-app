package com.github.lucasbarbosaalves.catalog.application.castmember.create;

import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMember;

public record CreateCastMemberOutput(String id) {

    public static  CreateCastMemberOutput from(final CastMember castMember) {
        return new CreateCastMemberOutput(castMember.getId().getValue());
    }
}

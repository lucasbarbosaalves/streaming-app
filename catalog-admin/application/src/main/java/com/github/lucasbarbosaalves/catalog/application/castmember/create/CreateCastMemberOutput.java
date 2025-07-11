package com.github.lucasbarbosaalves.catalog.application.castmember.create;

import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMember;
import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberID;

public record CreateCastMemberOutput(String id) {

    public static  CreateCastMemberOutput from(final CastMember castMember) {
        return from(castMember.getId());
    }

    public static CreateCastMemberOutput from(final CastMemberID id) {
        return new CreateCastMemberOutput(id.getValue());
    }
}

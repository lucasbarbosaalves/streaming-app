package com.github.lucasbarbosaalves.catalog.application.castmember.update;

import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMember;

public record UpdateCastMemberOutput(String id) {

    public static UpdateCastMemberOutput from(final CastMember member) {
        return new UpdateCastMemberOutput(
                member.getId().getValue()
        );
    }
}

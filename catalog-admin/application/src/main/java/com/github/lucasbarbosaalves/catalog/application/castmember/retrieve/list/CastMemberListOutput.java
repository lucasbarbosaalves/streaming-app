package com.github.lucasbarbosaalves.catalog.application.castmember.retrieve.list;

import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMember;
import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberType;

import java.time.Instant;

public record CastMemberListOutput(
        String id,
        String name,
        CastMemberType type,
        Instant createdAt
) {

    public static CastMemberListOutput from(CastMember member) {
        return new CastMemberListOutput(
                member.getId().getValue(),
                member.getName(),
                member.getType(),
                member.getCreatedAt()
        );
    }
}

package com.github.lucasbarbosaalves.catalog.infrastructure.castmember.models;

import com.github.lucasbarbosaalves.catalog.domain.castmember.CastMemberType;

public record CreateCastMemberRequest(
        String name,
        CastMemberType type
) {
}

package com.github.lucasbarbosaalves.catalog.infrastructure.castmember.models;

public record CastMemberResponse(
        String id,
        String name,
        String type,
        String createdAt,
        String updatedAt
) {
}
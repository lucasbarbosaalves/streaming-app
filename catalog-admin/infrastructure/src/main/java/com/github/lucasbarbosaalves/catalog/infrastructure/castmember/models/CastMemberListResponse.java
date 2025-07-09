package com.github.lucasbarbosaalves.catalog.infrastructure.castmember.models;

public record CastMemberListResponse(
        String id,
        String name,
        String type,
        String createdAt
) {
}
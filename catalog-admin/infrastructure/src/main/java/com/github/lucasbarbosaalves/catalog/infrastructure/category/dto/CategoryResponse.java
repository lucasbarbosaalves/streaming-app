package com.github.lucasbarbosaalves.catalog.infrastructure.category.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record CategoryResponse(
        @JsonProperty(value = "id") String id,
        @JsonProperty(value = "name") String name,
        @JsonProperty(value = "description") String description,
        @JsonProperty(value = "is_active") Boolean isActive,
        @JsonProperty(value = "created_at") Instant createdAt,
        @JsonProperty(value = "updated_at") Instant updatedAt,
        @JsonProperty(value = "deleted_at") Instant deletedAt
) {
}

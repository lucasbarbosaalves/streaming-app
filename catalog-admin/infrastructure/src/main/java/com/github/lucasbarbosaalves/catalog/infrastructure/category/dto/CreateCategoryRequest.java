package com.github.lucasbarbosaalves.catalog.infrastructure.category.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCategoryRequest(
        @JsonProperty(value = "name") String name,
        @JsonProperty(value = "description") String description,
        @JsonProperty(value = "is_active") Boolean active
) {
}

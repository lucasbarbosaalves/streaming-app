package com.github.lucasbarbosaalves.catalog.domain.category;

public record CategorySearchQuery(int page, int perPage, String terms, String sort, String direction) {

    public static CategorySearchQuery with(final int page, final int perPage, final String terms, final String sort, final String direction) {
        return new CategorySearchQuery(page, perPage, terms, sort, direction);
    }
}

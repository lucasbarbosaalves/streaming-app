package com.github.lucasbarbosaalves.catalog.domain.video;

public record VideoSearchQuery(int page, int perPage, String terms, String sort, String direction) {

    public static VideoSearchQuery with(final int page, final int perPage, final String terms, final String sort, final String direction) {
        return new VideoSearchQuery(page, perPage, terms, sort, direction);
    }
}

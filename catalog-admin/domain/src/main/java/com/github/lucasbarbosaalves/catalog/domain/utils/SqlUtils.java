package com.github.lucasbarbosaalves.catalog.domain.utils;

public final class SqlUtils {

    private SqlUtils() {
        // Utility class, prevent instantiation
    }

    public static String like(final String term) {
        if (term == null) return null;
        return "%" + term + "%";
    }
}

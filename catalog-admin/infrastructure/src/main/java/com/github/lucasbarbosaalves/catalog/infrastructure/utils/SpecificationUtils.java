package com.github.lucasbarbosaalves.catalog.infrastructure.utils;

import org.springframework.data.jpa.domain.Specification;

public final class SpecificationUtils {

    private SpecificationUtils () {
    }

    public static <T>Specification<T> like(final String prop, final String term) {
        return ((root, query, cb) -> cb.like(cb.upper(root.get(prop)), getPattern(term.toUpperCase())));
    }

    private static String getPattern(final String term) {
        return "%" + term + "%";
    }

}

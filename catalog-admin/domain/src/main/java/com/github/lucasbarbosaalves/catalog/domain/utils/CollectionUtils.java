package com.github.lucasbarbosaalves.catalog.domain.utils;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CollectionUtils {

    private CollectionUtils() {
    }

    public static <IN, OUT> Set<OUT> mapTo(final Set<IN> ids, final Function<IN, OUT> mapper) {
        if (ids == null) {
            return null;
        }
        return ids.stream()
                .map(mapper)
                .collect(Collectors.toSet());
    }

    public static <T> Set<T> nullIfEmpty(final Set<T> values) {
        return values == null || values.isEmpty() ? null : values;
    }

}

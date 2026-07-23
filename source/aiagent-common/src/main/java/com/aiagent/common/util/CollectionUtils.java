package com.aiagent.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Collection utility methods.
 */
public final class CollectionUtils {

    private CollectionUtils() {
        // Utility class
    }

    /**
     * Check if a collection is null or empty.
     */
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Return an unmodifiable list, or empty list if null.
     */
    public static <T> List<T> nullSafe(List<T> list) {
        return list != null ? Collections.unmodifiableList(list) : List.of();
    }

    /**
     * Map a collection to a new list using the given mapper.
     */
    public static <T, R> List<R> map(Collection<T> source, Function<T, R> mapper) {
        Objects.requireNonNull(source, "source must not be null");
        Objects.requireNonNull(mapper, "mapper must not be null");
        return source.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * Partition a list into sublists of the given size.
     */
    public static <T> List<List<T>> partition(List<T> list, int batchSize) {
        Objects.requireNonNull(list, "list must not be null");
        if (batchSize < 1) {
            throw new IllegalArgumentException("batchSize must be >= 1");
        }
        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += batchSize) {
            partitions.add(list.subList(i, Math.min(i + batchSize, list.size())));
        }
        return Collections.unmodifiableList(partitions);
    }

    /**
     * Get the first element or null.
     */
    public static <T> T firstOrNull(List<T> list) {
        return isNullOrEmpty(list) ? null : list.getFirst();
    }
}

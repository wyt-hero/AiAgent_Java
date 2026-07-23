package com.aiagent.common.dto;

import java.util.List;
import java.util.Objects;

/**
 * Paginated result wrapper.
 *
 * @param <T> the type of elements in the page
 */
public record PageResult<T>(
        List<T> items,
        long total,
        int page,
        int size,
        int totalPages
) {

    public PageResult {
        Objects.requireNonNull(items, "items must not be null");
        if (page < 1) {
            throw new IllegalArgumentException("page must be >= 1, got: %d".formatted(page));
        }
        if (size < 1) {
            throw new IllegalArgumentException("size must be >= 1, got: %d".formatted(size));
        }
        items = List.copyOf(items);
    }

    /**
     * Create a paginated result.
     */
    public static <T> PageResult<T> of(List<T> items, long total, int page, int size) {
        int totalPages = size > 0 ? (int) Math.ceil((double) total / size) : 0;
        return new PageResult<>(items, total, page, size, totalPages);
    }

    /**
     * Create an empty page.
     */
    public static <T> PageResult<T> empty(int page, int size) {
        return new PageResult<>(List.of(), 0, page, size, 0);
    }

    /**
     * Whether there is a next page.
     */
    public boolean hasNext() {
        return page < totalPages;
    }

    /**
     * Whether there is a previous page.
     */
    public boolean hasPrevious() {
        return page > 1;
    }
}

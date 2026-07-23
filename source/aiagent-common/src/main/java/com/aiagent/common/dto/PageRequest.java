package com.aiagent.common.dto;

import java.util.Objects;

/**
 * Standard page request parameters.
 */
public record PageRequest(
        int page,
        int size,
        String sortBy,
        SortDirection sortDirection
) {

    public PageRequest {
        if (page < 1) {
            throw new IllegalArgumentException("page must be >= 1, got: %d".formatted(page));
        }
        if (size < 1 || size > 100) {
            throw new IllegalArgumentException("size must be between 1 and 100, got: %d".formatted(size));
        }
        sortDirection = sortDirection != null ? sortDirection : SortDirection.ASC;
    }

    /**
     * Create a default page request.
     */
    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size, null, SortDirection.ASC);
    }

    /**
     * Create a sorted page request.
     */
    public static PageRequest of(int page, int size, String sortBy, SortDirection sortDirection) {
        return new PageRequest(page, size, sortBy, sortDirection);
    }

    /**
     * Calculate the offset for database queries.
     */
    public long offset() {
        return (long) (page - 1) * size;
    }

    /**
     * Sort direction.
     */
    public enum SortDirection {
        ASC, DESC
    }
}

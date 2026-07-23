package com.aiagent.common.dto;

import java.util.List;
import java.util.Objects;

/**
 * Cursor-based pagination result.
 * Suitable for large datasets where offset-based pagination is inefficient.
 *
 * <p>Usage pattern:
 * <pre>{@code
 * // First page
 * CursorResult<User> result = service.listUsers(null, 20);
 *
 * // Next page
 * if (result.hasMore()) {
 *     CursorResult<User> nextPage = service.listUsers(result.nextCursor(), 20);
 * }
 * }</pre>
 *
 * @param <T> the type of elements
 */
public record CursorResult<T>(
        List<T> items,
        String nextCursor,
        String previousCursor,
        int size,
        boolean hasMore
) {

    public CursorResult {
        Objects.requireNonNull(items, "items must not be null");
        if (size < 1) {
            throw new IllegalArgumentException("size must be >= 1, got: %d".formatted(size));
        }
        items = List.copyOf(items);
    }

    /**
     * Create a cursor result with both cursors.
     */
    public static <T> CursorResult<T> of(List<T> items, String nextCursor, String previousCursor,
                                          int size, boolean hasMore) {
        return new CursorResult<>(items, nextCursor, previousCursor, size, hasMore);
    }

    /**
     * Create a cursor result with only next cursor (forward pagination).
     */
    public static <T> CursorResult<T> forward(List<T> items, String nextCursor, int size, boolean hasMore) {
        return new CursorResult<>(items, nextCursor, null, size, hasMore);
    }

    /**
     * Create an empty cursor result.
     */
    public static <T> CursorResult<T> empty(int size) {
        return new CursorResult<>(List.of(), null, null, size, false);
    }

    /**
     * Whether there are no items.
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Get the number of items in this result.
     */
    public int itemCount() {
        return items.size();
    }
}

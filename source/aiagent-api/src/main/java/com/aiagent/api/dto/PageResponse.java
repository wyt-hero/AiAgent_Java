package com.aiagent.api.dto;

import com.aiagent.common.dto.PageResult;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

/**
 * API-layer paginated response wrapper.
 *
 * <p>Wraps a {@link PageResult} for REST API responses with pagination metadata.
 *
 * @param <T> the type of elements
 */
@Schema(description = "Paginated API response")
public record PageResponse<T>(
        @Schema(description = "List of items in current page") List<T> content,
        @Schema(description = "Current page number (1-based)") int page,
        @Schema(description = "Page size") int size,
        @Schema(description = "Total number of elements") long totalElements,
        @Schema(description = "Total number of pages") int totalPages,
        @Schema(description = "Whether there is a next page") boolean hasNext,
        @Schema(description = "Whether there is a previous page") boolean hasPrevious
) {

    public PageResponse {
        Objects.requireNonNull(content, "content must not be null");
        content = List.copyOf(content);
    }

    /**
     * Create a page response from a PageResult.
     */
    public static <T> PageResponse<T> from(PageResult<T> pageResult) {
        return new PageResponse<>(
                pageResult.items(),
                pageResult.page(),
                pageResult.size(),
                pageResult.total(),
                pageResult.totalPages(),
                pageResult.hasNext(),
                pageResult.hasPrevious()
        );
    }

    /**
     * Create a page response with explicit parameters.
     */
    public static <T> PageResponse<T> of(List<T> content, int page, int size, long totalElements) {
        int totalPages = size > 0 ? (int) Math.ceil((double) totalElements / size) : 0;
        boolean hasNext = page < totalPages;
        boolean hasPrevious = page > 1;
        return new PageResponse<>(content, page, size, totalElements, totalPages, hasNext, hasPrevious);
    }

    /**
     * Create an empty page response.
     */
    public static <T> PageResponse<T> empty(int page, int size) {
        return new PageResponse<>(List.of(), page, size, 0, 0, false, false);
    }
}

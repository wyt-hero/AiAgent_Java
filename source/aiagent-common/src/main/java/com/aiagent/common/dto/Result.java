package com.aiagent.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * Unified API response wrapper.
 *
 * @param <T> the type of response data
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Result<T>(
        boolean success,
        int code,
        String message,
        T data,
        Map<String, Object> metadata,
        OffsetDateTime timestamp
) {

    public Result {
        Objects.requireNonNull(message, "message must not be null");
        metadata = metadata != null ? Map.copyOf(metadata) : null;
        timestamp = timestamp != null ? timestamp : OffsetDateTime.now();
    }

    /**
     * Create a success response with data.
     */
    public static <T> Result<T> ok(T data) {
        return new Result<>(true, 0, "success", data, null, OffsetDateTime.now());
    }

    /**
     * Create a success response with data and message.
     */
    public static <T> Result<T> ok(T data, String message) {
        return new Result<>(true, 0, message, data, null, OffsetDateTime.now());
    }

    /**
     * Create a success response without data.
     */
    public static <T> Result<T> ok() {
        return new Result<>(true, 0, "success", null, null, OffsetDateTime.now());
    }

    /**
     * Create an error response.
     */
    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(false, code, message, null, null, OffsetDateTime.now());
    }

    /**
     * Create an error response with metadata.
     */
    public static <T> Result<T> fail(int code, String message, Map<String, Object> metadata) {
        return new Result<>(false, code, message, null, metadata, OffsetDateTime.now());
    }
}
